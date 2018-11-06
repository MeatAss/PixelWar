function sendTableWelcomeData() {
    stompClient.send("/app/welcome/data", {}, {});
}

function readTableWelcomeData(message) {
    $(JSON.parse(message.body)).each(function(i, item) {
        createNewTR(item);
    });
}

function createNewTR(message) {
    tr = $('<tr></tr>').attr('value', message.id);
    tr.append($('<td></td>').text(message.nameImg));
    tr.append($('<td></td>')
        .append(createCanvasWirhDataURL(message.dataImg))
    );
    //tr.append($('<td></td>').text(message.dataImg));//Create mini canvas!!!!
    tr.append($('<td></td>').text(message.countLobby));
    tr.append($('<td></td>')
        .append($('<i></i>')
            .addClass('fas fa-plus')
            .on('click', function() {
                connectLobby(message.id);
            })
        )
    );
    tr.append($('<td></td>')
        .append($('<i></i>')
            .addClass('far fa-trash-alt')
            .on('click', function() {
                onDelete(message.id);
            })
        )
    );

    $('#welcomeTableTbody').append(tr);
}

function createCanvasWirhDataURL(dataUrl) {
    let canvasElem = $('<canvas></canvas>');
    $(canvasElem).width(50);
    $(canvasElem).height(50);

    let contextElem = canvasElem[0].getContext('2d');
    contextElem.scale(0.21, 0.21);
    let imageObj = new Image();

    imageObj.onload = function() {
        contextElem.drawImage(this, 0, 0);
    };
    imageObj.src = dataUrl;

    return canvasElem;
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

function readNewTR(message) {
    createNewTR(JSON.parse(message.body));
}

function connect() {
    socket = new SockJS('/gs-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe("/user/queue/welcome/data", readTableWelcomeData);
        stompClient.subscribe('/topic/welcome/remove', readRemoveTR);
        stompClient.subscribe('/topic/welcome/new', readNewTR);
        stompClient.subscribe('/user/queue/welcome/error/alert', function(message) {
            alert(JSON.parse(message.body).message);
        });
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

function createImageClick() {
    stompClient.send("/app/welcome/createImage", {}, JSON.stringify({'message' : $('#newImageName').val()}));
}