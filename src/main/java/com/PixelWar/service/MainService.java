package com.PixelWar.service;

import com.PixelWar.domain.CanvasData;
import com.PixelWar.domain.Lobby;
import com.PixelWar.domain.LobbyMessage;
import com.PixelWar.repository.ImageRepository;
import com.PixelWar.repository.LobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private LobbyRepository lobbyRepository;

    public String tryAdd(Long id) {
        if (!imageRepository.findById(id).isPresent())
            return "Lobby not found!";

        if (lobbyRepository.countByidLobby(id) >= Lobby.maxCountConnections)
            return "Lobby is full";

        return "";
    }

    public boolean connect(Long id, String connection, String path) {
        if (lobbyRepository.countByidLobby(id) >= Lobby.maxCountConnections)
            return false;

        lobbyRepository.save(new Lobby(id, connection));
        sendChangeLobbyCount(id, path);

        return true;
    }

    public void disconnect(Long id, String connection, String path) {
        lobbyRepository.deleteByconnection(connection);
        sendChangeLobbyCount(id, path);
    }

    private void sendChangeLobbyCount(Long id, String path) {
        simpMessagingTemplate.convertAndSend(path, new LobbyMessage(
                id,
                lobbyRepository.countByidLobby(id) + " : " + Lobby.maxCountConnections)
        );
    }

    public void sendCanvasData(String connection, CanvasData canvasData) {
        lobbyRepository.findAllByidLobby(canvasData.getId()).forEach(item -> {
            if (item.getConnection() != connection)
                simpMessagingTemplate.convertAndSendToUser(
                        item.getConnection(),
                        "/queue/main/" + canvasData.getId() + "/update",
                        canvasData);
        });
    }
}