package com.PixelWar.service;

import com.PixelWar.domain.CanvasData;
import com.PixelWar.domain.Lobby;
import com.PixelWar.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {
    private List<Lobby> lobbys = new ArrayList<Lobby>();

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public String tryAdd(Long id) {
        if (!imageRepository.findById(id).isPresent())
            return "Lobby not found!";

        if (getCountById(id) >= 1)
            return getFirstById(id).tryAdd() ? "" : "Lobby is full";

        lobbys.add(new Lobby(id));

        return "";
    }

    public boolean connect(Long id, String connection, String path) {
        if (getCountById(id) >= 1)
            return getFirstById(id).tryAdd(connection, simpMessagingTemplate, path);

        lobbys.add(new Lobby(id, connection));

        return true;
    }

    public void disconnect(Long id, String connection) {
        if (getCountById(id) >= 1)
            getFirstById(id).remove(connection);
    }

    private long getCountById(Long id) {
        return lobbys.stream().filter(item -> item.getId() == id).count();
    }

    private Lobby getFirstById(Long id) {
        return lobbys.stream().filter(item -> item.getId() == id).findFirst().get();
    }

    public void sendCanvasData(String connection, CanvasData canvasData) {
        getFirstById(canvasData.getId()).getConnections().forEach(item -> {
                if (item != connection)
                    simpMessagingTemplate.convertAndSendToUser(item, "/queue/main/"+canvasData.getId()+"/update", canvasData);
        });
    }
}
