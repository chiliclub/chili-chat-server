package com.chiliclub.chilichat.repository;

import com.chiliclub.chilichat.model.ChatRoomFindResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomJpaRepositoryTest {

    @Autowired
    CustomJpaRepository customJpaRepository;

    @Test
    void 채팅방_목록_조회() {
        List<ChatRoomFindResponse> result = customJpaRepository.findChatRoomListWithAdminAndParticipantCount();

        for (ChatRoomFindResponse chatRoomFindResponse : result) {
            System.out.println("chatRoomFindResponse = " + chatRoomFindResponse.getTotalParticipantCount());
            System.out.println("chatRoomFindResponse.getTitle() = " + chatRoomFindResponse.getTitle());
        }
    }

}