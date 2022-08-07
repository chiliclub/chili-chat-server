package com.chiliclub.chilichat.model;

import com.chiliclub.chilichat.entity.ChatRoomEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ApiModel
@AllArgsConstructor
@Builder
public class ChatRoomFindResponse {

    private String title;

    private Timestamp insDatetime;

    private Timestamp updDatetime;

    private BigInteger adminUserNo;

    private int totalParticipantCount;

    public ChatRoomFindResponse(String title, Timestamp insDatetime, Timestamp updDatetime, BigInteger adminUserNo, Integer totalParticipantCount) {
        this.title = title;
        this.insDatetime = insDatetime;
        this.updDatetime = updDatetime;
        this.adminUserNo = adminUserNo;
        this.totalParticipantCount = totalParticipantCount;
    }
}
