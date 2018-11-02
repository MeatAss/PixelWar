package com.PixelWar.controller;

import com.PixelWar.domain.SearchMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class MainController {

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        return "main";
    }

    @MessageMapping("/message")
    @SendTo("/topic/message")
    public SearchMessage information(SearchMessage searchMessage) throws Exception {
        return new SearchMessage(searchMessage.getMessage(), searchMessage.getId());
    }
}
