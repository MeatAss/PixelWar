package com.PixelWar.controller;

import com.PixelWar.domain.Lobby;
import com.PixelWar.domain.SimpleMessage;
import com.PixelWar.repository.ImageRepository;
import com.PixelWar.repository.LobbyRepository;
import com.PixelWar.service.AsuncReadAllDB;
import com.PixelWar.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class WelcomeController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private LobbyRepository lobbyRepository;

    @GetMapping({"/welcome", "/"})
    public String main(Map<String, Object> model) {
        return "welcome";
    }

    @MessageMapping("/welcome/data")
    public void prepareDBtoTranslate(Principal principal) throws Exception {

        Thread thread = new Thread(new AsuncReadAllDB(
                simpMessagingTemplate,
                imageRepository,
                "/queue/welcome/data",
                principal.getName(),
                lobbyRepository
        ));

        thread.start();
    }

    @MessageMapping("/welcome/remove")
    public void removeById(Principal principal, SimpleMessage message) throws Exception {
        if (lobbyRepository.countByidLobby(Long.parseLong(message.getMessage())) > 0) {
            simpMessagingTemplate.convertAndSendToUser(
                    principal.getName(),
                    "/queue/welcome/remove/error",
                    new SimpleMessage("Remove is denied! There are people in the lobby"));
            return;
        }


        imageRepository.deleteById(Long.parseLong(message.getMessage()));
        simpMessagingTemplate.convertAndSend("/topic/welcome/remove", message);
    }
}