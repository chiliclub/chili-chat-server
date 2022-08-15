package com.chiliclub.chilichat.controller;

import com.chiliclub.chilichat.model.MessageRequest;
import com.chiliclub.chilichat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;


    Logger logger = LoggerFactory.getLogger(MessageController.class);

    // 실제 소켓 통신 요청 -> pub/chat/message
    @MessageMapping({"chat/message"})
    public void sendMessage(MessageRequest messageRequest) {
        messageService.send(messageRequest);
    }
}
