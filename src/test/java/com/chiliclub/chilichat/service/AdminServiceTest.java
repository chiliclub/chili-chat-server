package com.chiliclub.chilichat.service;

import com.chiliclub.chilichat.common.exception.ResourceNotFoundException;
import com.chiliclub.chilichat.entity.AdminEntity;
import com.chiliclub.chilichat.entity.ChatRoomEntity;
import com.chiliclub.chilichat.entity.UserEntity;
import com.chiliclub.chilichat.repository.AdminRepository;
import com.chiliclub.chilichat.repository.ChatRoomRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;
    @Mock
    private ChatRoomRepository chatRoomRepository;
    @Spy
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AdminService adminService;

    private AdminEntity createAdminEntity(UserEntity userEntity, ChatRoomEntity chatRoomEntity, Long adminNo) {

        AdminEntity adminEntity = AdminEntity.builder()
                .user(userEntity)
                .chatRoom(chatRoomEntity)
                .build();
        ReflectionTestUtils.setField(adminEntity, "no", adminNo);

        return adminEntity;
    }

    private ChatRoomEntity createChatRoomEntity(Long chatRoomNo) {

        ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
                .title("test title")
                .build();
        ReflectionTestUtils.setField(chatRoomEntity, "no", chatRoomNo);

        return chatRoomEntity;
    }

    private UserEntity createUserEntity(Long userNo) {

        UserEntity userEntity = UserEntity.create(
                "tester1",
                "adsfas123",
                "무키무키",
                passwordEncoder,
                "defaultPicUrl"
        );

        ReflectionTestUtils.setField(userEntity, "no", userNo);

        return userEntity;
    }

    @Test
    @DisplayName("유저 ID로 방장 정보를 조회하는 데 성공한다")
    void testSuccessToFindAdmin() {

        // given
        Long userNo = 1L;
        AdminEntity mockAdminEntity = mock(AdminEntity.class);

        given(adminRepository.findByUserNo(userNo))
                .willReturn(Optional.ofNullable(mockAdminEntity));

        // when
        AdminEntity foundAdmin = adminService.findAdmin(userNo);

        // then
        assertThat(foundAdmin).isEqualTo(mockAdminEntity);
    }

    @Test
    @DisplayName("유저 ID로 방장 정보를 조회하는 데 실패한다")
    void testFailToFindAdminIfAdminIsNotExist() {

        // given
        Long userNo = 1L;

        given(adminRepository.findByUserNo(userNo))
                .willReturn(Optional.empty());

        // when && then
        assertThatThrownBy(() -> adminService.findAdmin(userNo))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("유저가 특정 채팅방의 방장이다")
    void testSuccessToCheckIsAdmin() {

        // given
        Long userNo = 1L;
        UserEntity userEntity = createUserEntity(userNo);

        Long chatRoomNo = 2L;
        ChatRoomEntity chatRoomEntity = createChatRoomEntity(chatRoomNo);

        Long adminNo = 3L;
        AdminEntity adminEntity = createAdminEntity(userEntity, chatRoomEntity, adminNo);

        given(adminRepository.findByUserNo(userNo))
                .willReturn(Optional.ofNullable(adminEntity));
        given(chatRoomRepository.findById(chatRoomNo))
                .willReturn(Optional.ofNullable(chatRoomEntity));

        // when
        boolean result = adminService.checkIsAdmin(userNo, chatRoomNo);

        // then
        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("유저가 특정 채팅방의 방장이 아니다")
    void testFailToCheckIsAdmin() {

        // given
        Long userNo = 1L;
        UserEntity userEntity = createUserEntity(userNo);

        Long chatRoomNo = 2L;
        ChatRoomEntity chatRoomEntity = createChatRoomEntity(chatRoomNo);

        Long adminNo = 3L;
        AdminEntity adminEntity = createAdminEntity(userEntity, chatRoomEntity, adminNo);

        given(adminRepository.findByUserNo(userNo))
                .willReturn(Optional.ofNullable(adminEntity));
        given(chatRoomRepository.findById(chatRoomNo))
                .willReturn(Optional.empty());

        // when && then
        assertThatThrownBy(() -> adminService.checkIsAdmin(userNo, chatRoomNo))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}