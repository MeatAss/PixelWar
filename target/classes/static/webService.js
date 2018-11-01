

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        $("#mainTable tbody").html("");
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/message', readMessage);
    });
}

function readMessage(message) {
    alert(JSON.stringify(message.body)[0]);
}

function sendSearchingText() {
    stompClient.send("/app/message", {}, JSON.stringify({'message': $("#search").val()}));
}

$( document ).ready(function () {
    connect();

    $("form").on('submit', function (e) {
        e.preventDefault();
        sendSearchingText();
    });
});