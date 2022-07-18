package com.chiliclub.chilichat.service;

import com.chiliclub.chilichat.common.exception.InvalidReqParamException;
import com.chiliclub.chilichat.entity.UserEntity;
import com.chiliclub.chilichat.model.user.UserSaveRequest;
import com.chiliclub.chilichat.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Spy
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    private UserSaveRequest createAddUserRequest() {
        return UserSaveRequest.builder()
                .id("tester1")
                .password("test1234")
                .nickname("무키무키")
                .build();
    }

    private UserEntity createUserEntity(UserSaveRequest req) {

        UserEntity userEntity = UserEntity.create(req, passwordEncoder);
        Long userEntityNo = 1L;
        ReflectionTestUtils.setField(userEntity, "no", userEntityNo);

        return userEntity;
    }


    @Test
    @DisplayName("정상적으로 유저를 저장한다")
    void testSuccessToSaveUser() {

        // given
        UserSaveRequest req = createAddUserRequest();
        UserEntity userEntity = createUserEntity(req);

        given(userRepository.findByLoginId(any(String.class)))
                .willReturn(Optional.empty());
        given(userRepository.findByNickname(any(String.class)))
                .willReturn(Optional.empty());
        given(userRepository.save(any(UserEntity.class)))
                .willReturn(userEntity);

        // when
        Long createdUserNo = userService.saveUser(req);

        // then
        assertThat(createdUserNo).isEqualTo(1L);
    }

    @Test
    @DisplayName("아이디 중복으로 유저를 저장하는 데 실패한다")
    void testFailToSaveUserIfIdIsDuplicated() {

        // given
        UserSaveRequest req = createAddUserRequest();
        UserEntity userEntity = createUserEntity(req);

        given(userRepository.findByLoginId(any(String.class)))
                .willReturn(Optional.ofNullable(userEntity));

        // when && then
        assertThatThrownBy(() -> userService.saveUser(req))
                .isInstanceOf(InvalidReqParamException.class)
                .hasMessage("중복된 아이디입니다.");
    }

    @Test
    @DisplayName("닉네임 중복으로 유저를 저장하는 데 실패한다")
    void testFailToSaveUserIfNicknameIsDuplicated() {

        // given
        UserSaveRequest req = createAddUserRequest();
        UserEntity userEntity = createUserEntity(req);

        given(userRepository.findByLoginId(any(String.class)))
                .willReturn(Optional.empty());
        given(userRepository.findByNickname(any(String.class)))
                .willReturn(Optional.ofNullable(userEntity));

        // when && then
        assertThatThrownBy(() -> userService.saveUser(req))
                .isInstanceOf(InvalidReqParamException.class)
                .hasMessage("중복된 닉네임입니다.");
    }
}