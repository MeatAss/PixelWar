package com.PixelWar.controller;

import com.PixelWar.domain.CanvasData;
import com.PixelWar.domain.SimpleIdMessage;
import com.PixelWar.domain.SimpleMessage;
import com.PixelWar.repository.ImageRepository;
import com.PixelWar.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MainService mainService;

    @RequestMapping(value = "/main/{id}", method = RequestMethod.GET)
    @ResponseStatus
    public String redirectToMain(@PathVariable("id") long id,
                                 Map<String, Object> model,
                                 HttpServletResponse httpServletResponse) throws IOException {

        String data = mainService.tryAdd(id);

        if (data.length() == 0)
            return "main";
        else {
            httpServletResponse.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE, data);
            return "";
        }
    }

    @MessageMapping("main/disconnect")
    public void disconnectLobby(Principal principal, SimpleIdMessage message) throws Exception {
        mainService.disconnect(
                message.getId(),
                message.getMessage(),
                principal.getName(),
                "/topic/welcome/updateTable"
        );
    }

    @MessageMapping("main/connect")
    public void connectLobby(Principal principal, SimpleMessage message) throws Exception {
        mainService.connect(
                principal.getName(),
                Long.parseLong(message.getMessage()),
                principal.getName(),
                "/topic/welcome/updateTable",
                "/queue/main/" + message.getMessage() + "/image"
        );
    }

    @MessageMapping("/main/update")
    public void updateImage(Principal principal, CanvasData canvasData) throws Exception {
        mainService.sendCanvasData(principal.getName(), canvasData);
    }
}