package com.chiliclub.chilichat.model;

import com.chiliclub.chilichat.entity.ChatRoomEntity;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel
public class ChatRoomUpdateResponse {

    private Long id;

    @Builder
    public ChatRoomUpdateResponse(Long id) {
        this.id = id;
    }

    public static ChatRoomUpdateResponse from(ChatRoomEntity chatRoom) {
        return ChatRoomUpdateResponse.builder()
                .id(chatRoom.getNo())
                .build();
    }
}
