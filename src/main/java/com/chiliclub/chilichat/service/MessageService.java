package com.chiliclub.chilichat.service;

import com.chiliclub.chilichat.controller.MessageController;
import com.chiliclub.chilichat.entity.MessageEntity;
import com.chiliclub.chilichat.model.MessageRequest;
import com.chiliclub.chilichat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;

    private final UserService userService;

    Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final SimpMessageSendingOperations messageSendingOperations;


    public void send(MessageRequest messageRequest) {

        messageRepository.save(MessageEntity.create(messageRequest, userService.getCurrentUserNo()));

        messageSendingOperations
                .convertAndSend("/sub/chat-room/" + messageRequest.getChatRoomNo(),
                        messageRequest.getMessage());

    }
}
