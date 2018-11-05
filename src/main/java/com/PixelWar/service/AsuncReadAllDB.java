package com.PixelWar.service;

import com.PixelWar.domain.*;
import com.PixelWar.repository.ImageRepository;
import com.PixelWar.repository.LobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AsuncReadAllDB implements Runnable {

    private SimpMessagingTemplate simpMessagingTemplate;

    private ImageRepository imageRepository;

    private String destination;

    private String userName;

    private LobbyRepository lobbyRepository;

    @Override
    public void run() {
        List<Image> allImages = imageRepository.findAll();
        List<SimpleIdMessage> lobbies = lobbyRepository.findIdLobbyCount();

        List<ImageMessage> imageMessages = new ArrayList<ImageMessage>();
        allImages.forEach(image -> imageMessages.add(
                new ImageMessage(
                        image,
                        getFirstById(lobbies, image.getId()).isPresent() ?
                                getFirstById(lobbies, image.getId()).get().getMessage() + " : " + Lobby.maxCountConnections :
                                "0 : " + Lobby.maxCountConnections
                )
        ));

        simpMessagingTemplate.convertAndSendToUser(userName, destination, imageMessages);
    }

    private Optional<SimpleIdMessage> getFirstById(List<SimpleIdMessage> lobbies, Long id) {
        return lobbies.stream()
                .filter(lobby -> lobby.getId().equals(id))
                .findFirst();
    }

    public AsuncReadAllDB(SimpMessagingTemplate simpMessagingTemplate,
                          ImageRepository imageRepository,
                          String destination,
                          String userName,
                          LobbyRepository lobbyRepository
    ) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.imageRepository = imageRepository;
        this.destination = destination;
        this.userName = userName;
        this.lobbyRepository = lobbyRepository;
    }
}
