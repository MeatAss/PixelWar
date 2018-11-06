var id;

$(document).ready(function () {
    id = location.href.substr(location.href.lastIndexOf('/') + 1);
    connect();
});

function connect() {
    socket = new SockJS('/gs-websocket');
    stompClient = Stomp.over(socket);
    //stompClient.debug = null;
    stompClient.connect({}, function (frame) {

        stompClient.send("/app/main/connect", {}, JSON.stringify({'message' : id}));
        stompClient.subscribe('/user/queue/main/'+ id +'/image', loadImage);
        stompClient.subscribe('/user/queue/main/'+ id +'/update', drawItemInCanvas);

        $(window).on('beforeunload', function() {

            stompClient.send('/app/main/disconnect', {}, JSON.stringify({
                'id' : id,
                'message' : stage.toDataURL()
            }));
            stompClient.disconnect();
        });
    });
}

function loadImage(dataURL) {
    img = new Image();
    img.src = JSON.parse(dataURL.body).message;
    image.fillPatternImage(img);
}

function sendUpdateCoordinates(id, moveTo, lineTo, size, color) {
    stompClient.send("/app/main/update", {}, JSON.stringify({
        'id' : id,
        'arrMoveTo' : moveTo,
        'arrLineTo' : lineTo,
        'size' : size,
        'color' : color,
    }));
}