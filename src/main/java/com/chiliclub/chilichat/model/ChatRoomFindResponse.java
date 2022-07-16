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

    private LocalDateTime upDatetime;

    @Builder
    public ChatRoomFindResponse(String title, LocalDateTime insDatetime, LocalDateTime upDatetime) {
        this.title = title;
        this.insDatetime = insDatetime;
        this.upDatetime = upDatetime;
    }

    public static ChatRoomFindResponse of(ChatRoomEntity chatRoom) {
        return ChatRoomFindResponse.builder()
                .title(chatRoom.getTitle())
                .insDatetime(chatRoom.getInsDatetime())
                .upDatetime(chatRoom.getUpdDatetime())
                .build();
    }
}
