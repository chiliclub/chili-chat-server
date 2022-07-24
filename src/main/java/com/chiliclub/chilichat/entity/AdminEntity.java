package com.chiliclub.chilichat.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "chat_room")
public class AdminEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_no")
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private UserEntity user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_no")
    private ChatRoomEntity chatRoom;

    @Builder
    public AdminEntity(UserEntity user, ChatRoomEntity chatRoom) {
        this.user = user;
        this.chatRoom = chatRoom;
    }

    public static AdminEntity create(UserEntity user, ChatRoomEntity chatRoom) {
        return AdminEntity.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();
    }
}
