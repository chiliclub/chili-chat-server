package com.chiliclub.chilichat.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "message")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_no")
    private Long no;

    private String title;

    private Long senderNo;

    private String senderNickname;

    private String senderPicUrl;

    private String message;

    private LocalDateTime insDatetime;


}
