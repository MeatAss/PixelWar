package com.PixelWar.controller;

import com.PixelWar.domain.Image;
import com.PixelWar.domain.SearchMessage;
import com.PixelWar.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Console;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        return "main";
    }

    @MessageMapping("/message")
    public void writeImage(SearchMessage searchMessage) throws Exception {
        long millisStart = System.currentTimeMillis() % 1000;

        Image image = new Image(searchMessage.getMessage());
        image.setId(searchMessage.getId());

        imageRepository.save(image);

        long millisEnd = System.currentTimeMillis() % 1000;
        System.out.println(millisStart - millisEnd);
    }

//    @MessageMapping("/message")
//    @SendTo("/topic/message")
//    public SearchMessage information(SearchMessage searchMessage) throws Exception {
//        return new SearchMessage(searchMessage.getMessage());
//    }
}
