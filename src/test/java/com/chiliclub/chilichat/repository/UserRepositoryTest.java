package com.chiliclub.chilichat.repository;

import com.chiliclub.chilichat.entity.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("userNo로 레코드 가져오기")
    void findByUserNoTest() {
        //given
        UserEntity user = UserEntity.builder()
                .loginId("테스트 아이디")
                .password("aaa")
                .nickname("닉네임")
                .build();

        UserEntity savedUser = userRepository.save(user);

        // when & then
        assertThat(userRepository.findByNo(savedUser.getNo()).getNickname()).isEqualTo("닉네임");
    }
}
