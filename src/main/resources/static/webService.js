function sendAll() {
    console.log("sssss");
    stompClient.send("/app/all", {}, JSON.stringify({'message': "asd", 'id': "1"}));
}

function readAll(test) {
    alert(JSON.parse(test.body).message);
}

function connect() {
    var socket = new SockJS('/gs-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        $("#mainTable tbody").html("");

        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/message', readMessage);
        stompClient.subscribe("/user/queue/reply", readAll);
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