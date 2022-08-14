package com.chiliclub.chilichat.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CustomJpaRepositoryTest {

    @Autowired
    CustomJpaRepository customJpaRepository;

//    @Test
//    void 채팅방_목록_조회() {
//        List<ChatRoomFindResponse> result = customJpaRepository.findChatRoomListWithAdminAndParticipantCount();
//
//        for (ChatRoomFindResponse chatRoomFindResponse : result) {
//            System.out.println("chatRoomFindResponse = " + chatRoomFindResponse.getTotalParticipantCount());
//            System.out.println("chatRoomFindResponse.getTitle() = " + chatRoomFindResponse.getTitle());
//        }
//    }

}