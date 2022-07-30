package com.chiliclub.chilichat.model;

import com.chiliclub.chilichat.entity.ChatRoomEntity;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ApiModel
public class ChatRoomFindResponse {

    private String title;

    private LocalDateTime insDatetime;

    private LocalDateTime updDatetime;

    @Builder
    public ChatRoomFindResponse(String title, LocalDateTime insDatetime, LocalDateTime updDatetime) {
        this.title = title;
        this.insDatetime = insDatetime;
        this.updDatetime = updDatetime;
    }

    public static ChatRoomFindResponse from(ChatRoomEntity chatRoom) {
        return ChatRoomFindResponse.builder()
                .title(chatRoom.getTitle())
                .insDatetime(chatRoom.getInsDatetime())
                .updDatetime(chatRoom.getUpdDatetime())
                .build();
    }
}
