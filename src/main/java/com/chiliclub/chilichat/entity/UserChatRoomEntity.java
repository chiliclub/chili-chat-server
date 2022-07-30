package com.chiliclub.chilichat.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "user_chat_room")
public class UserChatRoomEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_chat_room_no")
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_no")
    private ChatRoomEntity chatRoom;

    @Builder
    public UserChatRoomEntity(Long no, UserEntity user, ChatRoomEntity chatRoom) {
        this.no = no;
        this.user = user;
        this.chatRoom = chatRoom;
    }
}
