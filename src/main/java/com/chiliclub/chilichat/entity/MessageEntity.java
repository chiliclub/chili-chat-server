package com.chiliclub.chilichat.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Builder
@AllArgsConstructor
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
