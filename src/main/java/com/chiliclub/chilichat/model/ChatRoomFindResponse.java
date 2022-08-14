package com.chiliclub.chilichat.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ApiModel
@AllArgsConstructor
@Builder
public class ChatRoomFindResponse {

    private String title;

    private LocalDateTime insDatetime;

    private LocalDateTime updDatetime;

    private Long adminUserNo;

    private int totalParticipantCount;

    public ChatRoomFindResponse(String title, LocalDateTime insDatetime, LocalDateTime updDatetime, Long adminUserNo, Integer totalParticipantCount) {
        this.title = title;
        this.insDatetime = insDatetime;
        this.updDatetime = updDatetime;
        this.adminUserNo = adminUserNo;
        this.totalParticipantCount = totalParticipantCount;
    }
}
