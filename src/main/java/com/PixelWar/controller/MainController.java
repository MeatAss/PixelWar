package com.PixelWar.controller;

import com.PixelWar.domain.SearchMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping({"/velcome", "/"})
    public String main(Map<String, Object> model) {
        return "velcome";
    }

    @MessageMapping("/message")
    @SendTo("/topic/message")
    public SearchMessage information(SearchMessage searchMessage) throws Exception {
        return new SearchMessage(searchMessage.getMessage(), searchMessage.getId());
    }

    @MessageMapping("/all")
    public void all(Principal principal, SearchMessage searchMessage) throws Exception {
        simpMessagingTemplate.convertAndSendToUser(
                principal.getName(),
                "/queue/reply",
                new SearchMessage("reply", 1)
        );
    }
}