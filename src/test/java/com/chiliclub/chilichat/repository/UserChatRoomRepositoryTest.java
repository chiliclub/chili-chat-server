package com.chiliclub.chilichat.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserChatRoomRepositoryTest {

    @Autowired
    private UserChatRoomRepository userChatRoomRepository;

    @Test
    @DisplayName("test findByNo")
    void findByNo() {
        userChatRoomRepository.findByNo(1L);
    }

}