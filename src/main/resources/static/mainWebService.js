var id;

function connect() {
    socket = new SockJS('/gs-websocket');
    stompClient = Stomp.over(socket);
    stompClient.debug = null;
    stompClient.connect({}, function (frame) {

        stompClient.send("/app/main/connect", {}, JSON.stringify({'message' : id}));

        stompClient.subscribe('/user/queue/main/'+ id +'/update', drawItemInCanvas);

        $(window).on('beforeunload', function() {
            stompClient.send('/app/main/disconnect', {}, JSON.stringify({'message' : id}));
            stompClient.disconnect();
        });
    });
}

$( document ).ready(function () {
    id = location.href.substr(location.href.lastIndexOf('/') + 1);
    connect();
});

function sendUpdateCoordinates(id, moveTo, lineTo, mode, size, color) {
    console.log('asd');
    stompClient.send("/app/main/update", {}, JSON.stringify({
        'id' : id,
        'arrMoveTo' : moveTo,
        'arrLineTo' : lineTo,
        'mode' : mode,
        'size' : size,
        'color' : color,
    }));
}