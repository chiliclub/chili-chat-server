package com.chiliclub.chilichat.entity;

import com.chiliclub.chilichat.model.MessageRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@AllArgsConstructor
@Table(name = "message")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_no")
    private Long no;

    private Long senderNo;

    private Long chatRoomNo;

    private String message;

    private LocalDateTime insDatetime;

    @Builder
    private MessageEntity(Long senderNo, Long chatRoomNo, String message) {
        this.senderNo = senderNo;
        this.message = message;
        this.chatRoomNo = chatRoomNo;
        this.insDatetime = LocalDateTime.now();
    }

    public static MessageEntity create(MessageRequest messageRequest, Long userNo) {
        return MessageEntity.builder()
                .senderNo(userNo)
                .chatRoomNo(messageRequest.getChatRoomNo())
                .message(messageRequest.getMessage())
                .build();
    }

}
