function sendTableWelcomeData() {
    stompClient.send("/app/welcome/data", {}, {});
}

function readTableWelcomeData(message) {
    $(JSON.parse(message.body)).each(function(i, item) {
        tr = $('<tr></tr>').attr('value', item.id);
        tr.append($('<td></td>').text(item.nameImg));
        tr.append($('<td></td>').text(item.dataImg));//Create mini canvas!!!!
        tr.append($('<td></td>').text("0 : 4"));//Read count people in lobby
        tr.append($('<td></td>')
            .append($('<i></i>')
                .addClass('fas fa-plus')
                .on('click', function() {
                    connectLobby(item.id);
                })
            )
        );
        tr.append($('<td></td>')
            .append($('<i></i>')
                .addClass('far fa-trash-alt')
                .on('click', function() {
                    onDelete(item.id);
                })
            )
        );

        $('#welcomeTableTbody').append(tr);
    });
}

function connectLobby(id) {
    window.location.href = "/main/" + id;
}

function onDelete(id) {
    stompClient.send("/app/welcome/remove", {}, JSON.stringify({'message' : id}));
}

function readRemoveTR(message) {
    $("#welcomeTableTbody tr[value='"+ JSON.parse(message.body).message +"']").remove();
}

function connect() {
    socket = new SockJS('/gs-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe("/user/queue/welcome/data", readTableWelcomeData);
        stompClient.subscribe('/topic/welcome/remove', readRemoveTR);
        stompClient.subscribe('/topic/welcome/updateTable', function(lobbyMessage) {
            updateDataTable(JSON.parse(lobbyMessage.body))
        });

        sendTableWelcomeData();
    });
}

function updateDataTable(lobbyMessage) {
    $($("#welcomeTableTbody tr[value=\'" + lobbyMessage.id + "'] td" )[2]).text(lobbyMessage.message);
}

$( document ).ready(function () {
    connect();
});