package com.PixelWar.domain;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private static final int maxCountConnections = 4;

    private Long id;
    private List<String> connections;

    public Lobby(Long id) {
        this.id = id;
        connections = new ArrayList<String>();


        System.out.println("Connections" + connections.size());
    }

    public Lobby(Long id, String firstConnection) {
        this.id = id;
        connections = new ArrayList<String>();
        connections.add(firstConnection);
    }

    public Long getId() {
        return id;
    }

    public int countConnections() {
        return connections.size();
    }

    public boolean tryAdd() {
        if (countConnections() >= maxCountConnections)
            return false;

        return true;
    }

    public boolean tryAdd(String connection,
                          SimpMessagingTemplate simpMessagingTemplate,
                          String path) {
        if (!tryAdd())
            return false;

        connections.add(connection);
        simpMessagingTemplate.convertAndSend(path, new LobbyMessage(
                id,
                countConnections() + " : " + maxCountConnections)
        );

        return true;
    }

    public void remove(String connection) {
        connections.remove(connection);
    }

    public List<String> getConnections() {
        return connections;
    }
}
