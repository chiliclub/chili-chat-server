package com.chiliclub.chilichat.repository;

import com.chiliclub.chilichat.entity.ChatRoomEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ChatRoomRepositoryTest {

    @Autowired
    ChatRoomRepository chatRoomRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    AdminRepository adminRepository;

//    private ChatRoomEntity createChatRoomEntity() {
//        return ChatRoomEntity.builder()
//                .title("ddd")
//                .build();
//    }
//
//    private UserEntity createUserEntity(String loginId, String nickname) {
//        return UserEntity.builder()
//                .loginId(loginId)
//                .nickname(nickname)
//                .password("qwe1234")
//                .picUrl("aaaaa")
//                .build();
//    }

    @BeforeEach
    void init() {
//        ChatRoomEntity chatRoomEntity1 = createChatRoomEntity();
//        ChatRoomEntity chatRoomEntity2 = createChatRoomEntity();
//        ChatRoomEntity chatRoomEntity3 = createChatRoomEntity();
//        ChatRoomEntity chatRoomEntity4 = createChatRoomEntity();
//        ChatRoomEntity chatRoomEntity5 = createChatRoomEntity();
//
//        UserEntity userEntity1 = createUserEntity("testid1", "nickname1");
//        UserEntity userEntity2 = createUserEntity("testid2", "nickname2");
//        UserEntity userEntity3 = createUserEntity("testid3", "nickname3");
//        UserEntity userEntity4 = createUserEntity("testid4", "nickname4");
//        UserEntity userEntity5 = createUserEntity("testid5", "nickname5");
//
//        AdminEntity adminEntity1 = AdminEntity.create(userEntity1, chatRoomEntity1);
//        AdminEntity adminEntity2 = AdminEntity.create(userEntity2, chatRoomEntity2);
//        AdminEntity adminEntity3 = AdminEntity.create(userEntity3, chatRoomEntity3);
//        AdminEntity adminEntity4 = AdminEntity.create(userEntity4, chatRoomEntity4);
//        AdminEntity adminEntity5 = AdminEntity.create(userEntity5, chatRoomEntity5);
//
//        userRepository.saveAll(List.of(
//                userEntity1, userEntity2, userEntity3, userEntity4, userEntity5
//        ));
//
//        chatRoomRepository.saveAll(List.of(
//                chatRoomEntity1, chatRoomEntity2, chatRoomEntity3, chatRoomEntity4, chatRoomEntity5
//        ));
//
//        adminRepository.saveAll(List.of(
//                adminEntity1, adminEntity2, adminEntity3, adminEntity4, adminEntity5
//        ));
    }


//    @Test
//    void fetchAllTest() {
//
//        List<ChatRoomFindResponse> chatRoomEntities = chatRoomRepository.fetchAll();
//
//        for (ChatRoomFindResponse chatRoomEntity : chatRoomEntities) {
//            System.out.println("chatRoomEntity = " + chatRoomEntity);
//        }
//    }

    @Test
    void test_findAll() {

        List<ChatRoomEntity> chatRoomEntities = chatRoomRepository.findAll();

        for (ChatRoomEntity chatRoomEntity : chatRoomEntities) {
            log.info("chatRoomEntity = {}", chatRoomEntity);
        }
    }

    @Test
    void test_fetchAll() {

        List<ChatRoomEntity> chatRoomEntities = chatRoomRepository.fetchAll();

        for (ChatRoomEntity chatRoomEntity : chatRoomEntities) {
            log.info("chatRoomEntity = {}", chatRoomEntity);
        }
    }
}
