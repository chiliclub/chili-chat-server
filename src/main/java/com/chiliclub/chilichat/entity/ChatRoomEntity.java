package com.chiliclub.chilichat.entity;

import com.chiliclub.chilichat.model.ChatRoomCreateRequest;
import com.chiliclub.chilichat.model.ChatRoomUpdateRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "chat_room")
public class ChatRoomEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_no")
    private Long no;

    @Pattern(
            regexp = "[^?a-zA-Z\\d/]{1,30}"
    )
    @NotNull
    @Size(min = 1, max = 30)
    private String title;

    @Builder
    public ChatRoomEntity(String title) {
        this.title = title;
    }

    public static ChatRoomEntity create(ChatRoomCreateRequest req) {
        return ChatRoomEntity.builder()
                .title(req.getTitle())
                .build();
    }

    public void update(ChatRoomUpdateRequest req) {
        this.title = req.getTitle();
    }
}
