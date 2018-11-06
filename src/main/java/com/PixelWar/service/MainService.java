package com.PixelWar.service;

import com.PixelWar.domain.CanvasData;
import com.PixelWar.domain.Lobby;
import com.PixelWar.domain.LobbyMessage;
import com.PixelWar.domain.SimpleMessage;
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

    public boolean connect(String username, Long id, String connection, String topicPath, String queuePath) {
        if (lobbyRepository.countByidLobby(id) >= Lobby.maxCountConnections)
            return false;

        lobbyRepository.save(new Lobby(id, connection));
        sendChangeLobbyCount(id, topicPath);

        simpMessagingTemplate.convertAndSendToUser(
                username,
                queuePath,
                new SimpleMessage(imageRepository.findById(id).get().getDataImg()));

        return true;
    }

    public void disconnect(Long id, String imageData, String connection, String path) {
        imageRepository.save(imageRepository.findById(id).get().setDataImg(imageData));

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