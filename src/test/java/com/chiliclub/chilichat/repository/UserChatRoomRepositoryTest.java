package com.chiliclub.chilichat.repository;

import com.chiliclub.chilichat.entity.ChatRoomEntity;
import com.chiliclub.chilichat.entity.UserChatRoomEntity;
import com.chiliclub.chilichat.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserChatRoomRepositoryTest {

    @Autowired
    private UserChatRoomRepository userChatRoomRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private ChatRoomEntity createChatRoomEntity() {
        return ChatRoomEntity.builder()
                .title("ddd")
                .build();
    }

    private UserEntity createUserEntity(String loginId, String nickname) {
        return UserEntity.create(
                loginId,
                "qwe1234",
                nickname,
                passwordEncoder,
                "aaaa"

        );
    }

    private UserChatRoomEntity createUserChatRoomEntity(UserEntity user, ChatRoomEntity chatRoom) {
        return UserChatRoomEntity.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();
    }

    @Test
    @DisplayName("test findByNo")
    void findByNo() {
        userChatRoomRepository.findByNo(1L);
    }

    @Test
    @DisplayName("count by chatRoom test")
    void countByChatRoom() {
        // given
        ChatRoomEntity chatRoomEntity = createChatRoomEntity();

        ChatRoomEntity savedChatRoom = chatRoomRepository.save(chatRoomEntity);

        UserEntity userEntity1 = createUserEntity("testA", "nickname1");
        UserEntity userEntity2 = createUserEntity("testB", "nickname2");
        UserEntity userEntity3 = createUserEntity("testC", "nickname3");
        UserEntity userEntity4 = createUserEntity("testD", "nickname4");
        UserEntity userEntity5 = createUserEntity("testE", "nickname5");

        userRepository.saveAll(List.of(userEntity1, userEntity2, userEntity3, userEntity4, userEntity5));

        UserChatRoomEntity userChatRoom1 = UserChatRoomEntity.builder()
                .user(userEntity1)
                .chatRoom(chatRoomEntity)
                .build();

        UserChatRoomEntity userChatRoom2 = UserChatRoomEntity.builder()
                .user(userEntity2)
                .chatRoom(chatRoomEntity)
                .build();

        UserChatRoomEntity userChatRoom3 = UserChatRoomEntity.builder()
                .user(userEntity3)
                .chatRoom(chatRoomEntity)
                .build();

        UserChatRoomEntity userChatRoom4 = UserChatRoomEntity.builder()
                .user(userEntity4)
                .chatRoom(chatRoomEntity)
                .build();

        UserChatRoomEntity userChatRoom5 = UserChatRoomEntity.builder()
                .user(userEntity5)
                .chatRoom(chatRoomEntity)
                .build();

        userChatRoomRepository.saveAll(List.of(userChatRoom1, userChatRoom2, userChatRoom3, userChatRoom4, userChatRoom5));

        // when && then
        assertThat(userChatRoomRepository.countByChatRoomNo(savedChatRoom.getNo())).isEqualTo(5);
    }

}