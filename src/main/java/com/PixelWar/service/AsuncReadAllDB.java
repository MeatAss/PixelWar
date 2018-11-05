package com.PixelWar.service;

import com.PixelWar.domain.Image;
import com.PixelWar.domain.Lobby;
import com.PixelWar.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

public class AsuncReadAllDB implements Runnable {

    private SimpMessagingTemplate simpMessagingTemplate;

    private ImageRepository imageRepository;

    private String destination;

    private String userName;

    private List<Lobby> lobbies;

    @Override
    public void run() {
        List<Image> allImages = imageRepository.findAll();
        simpMessagingTemplate.convertAndSendToUser(userName, destination, allImages);
    }

    public AsuncReadAllDB(SimpMessagingTemplate simpMessagingTemplate,
                          ImageRepository imageRepository,
                          String destination,
                          String userName,
                          List<Lobby> lobbies) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.imageRepository = imageRepository;
        this.destination = destination;
        this.userName = userName;
        this.lobbies = lobbies;
    }
}
