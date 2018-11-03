var isPaint = false;
var lastPointerPosition;
var context = null;

var tim;

$(document).ready(function() {
    createCanvas('divCanvas', '#idTool');
});

function createCanvas(idContainer, idTool) {
    configureIdTool(idTool);

    stage = new Konva.Stage({
        container: idContainer,
        width: $('#' + idContainer).width(),
        height: $('#' + idContainer).height()
    });

    layer = new Konva.Layer();
    stage.add(layer);

    canvas = document.createElement('canvas');
    canvas.width = stage.width();
    canvas.height = stage.height();

    image = new Konva.Image({
        image: canvas,
        x: (stage.width() - (stage.width() / 2)) - (canvas.width / 2),
        y: (stage.height() - (stage.height() / 2)) - (canvas.height / 2),
        stroke: 'blue',
        shadowBlur: 5
    });
    layer.add(image);
    stage.draw();

    context = canvas.getContext('2d');
    changeContextParam(idTool);
    context.lineJoin = "round";

    image.on('mousedown touchstart', function () {
        isPaint = true;
        lastPointerPosition = stage.getPointerPosition();

    });

    stage.addEventListener('mouseup touchend', function () {
        isPaint = false;
    });

    stage.addEventListener('mousemove touchmove', function () {
        if (!isPaint) {
            return;
        }        
        
        context.beginPath();

        var localPos = {
            x: lastPointerPosition.x - image.x(),
            y: lastPointerPosition.y - image.y()
        };
        context.moveTo(localPos.x, localPos.y);
        var pos = stage.getPointerPosition();
        localPos = {
            x: pos.x - image.x(),
            y: pos.y - image.y()
        };
        context.lineTo(localPos.x, localPos.y);
        context.closePath();
        context.stroke();


        lastPointerPosition = pos;
        layer.batchDraw();
    });

    $(idTool).on('change', function () {        
        changeContextParam(idTool);
    });
}

function changeContextParam(idTool) {
    checkedElem = $(idTool + ' input[type=radio]:checked');
    context.strokeStyle = checkedElem.parent().find('i').css('color');

    if (checkedElem.val() === 'brush') {
        context.globalCompositeOperation = 'source-over';
    }
    if (checkedElem.val() === 'eraser') {
        context.globalCompositeOperation = 'destination-out';
    }
}

function configureIdTool(idTool) {
    $(idTool + ' label').each(function(index, elem) {

        elem.onmousedown=function(){
            tim=setTimeout( function() {  }, 1000);
        };
        elem.onmouseup=function(){
            clearTimeout(tim);
        };
        elem.onmouseleave=function(){
            clearTimeout(tim);
        };
    });
}

function changeSizeBrush(size) {
    context.lineWidth = size;
}

function sendCanvas() {
    elem = $('canvas')[0].toDataURL();
    console.log(elem);
    stompClient.send("/app/message", {}, JSON.stringify({'message': elem, 'id': 317}));
    setTimeout(sendCanvas, 33);
}