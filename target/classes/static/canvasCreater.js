var isPaint = false;
var lastPointerPosition;
var mode = 'brush';
var context = null;

    function createCanvas(idContainer, idTool) {
    stage = new Konva.Stage({
        container: idContainer,
        width: $('#' + idContainer).width(),
        height: $('#' + idContainer).height()
    });

    layer = new Konva.Layer();
    stage.add(layer);

    canvas = document.createElement('canvas');
    canvas.width = stage.width() - (stage.width() / 6);
    canvas.height = stage.height() - (stage.height() / 6);

    image = new Konva.Image({
        image: canvas,
        x: (stage.width() - (stage.width() / 2)) - (canvas.width / 2),
        y: (stage.height() - (stage.height() / 2)) - (canvas.height / 2),
        stroke: 'green',
        shadowBlur: 5
    });
    layer.add(image);
    stage.draw();

    context = canvas.getContext('2d');
    context.strokeStyle = "#df4b26";
    context.lineJoin = "round";
    context.lineWidth = 50;

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

        if (mode === 'brush') {
            context.globalCompositeOperation = 'source-over';
        }
        if (mode === 'eraser') {
            context.globalCompositeOperation = 'destination-out';
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

    select = document.getElementById(idTool);
    select.addEventListener('change', function () {
        mode = select.value;
    });
}

function sendCanvas() {
    elem = $('canvas')[0].toDataURL();
    console.log(elem);
    stompClient.send("/app/message", {}, JSON.stringify({'message': elem, 'id': 317}));
    setTimeout(sendCanvas, 33);
}