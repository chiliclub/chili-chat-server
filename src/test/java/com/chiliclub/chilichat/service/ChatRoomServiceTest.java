package com.chiliclub.chilichat.service;

import com.chiliclub.chilichat.common.exception.RequestForbiddenException;
import com.chiliclub.chilichat.common.exception.ResourceNotFoundException;
import com.chiliclub.chilichat.entity.AdminEntity;
import com.chiliclub.chilichat.entity.ChatRoomEntity;
import com.chiliclub.chilichat.entity.UserChatRoomEntity;
import com.chiliclub.chilichat.entity.UserEntity;
import com.chiliclub.chilichat.model.ChatRoomFindResponse;
import com.chiliclub.chilichat.model.ChatRoomUpdateRequest;
import com.chiliclub.chilichat.repository.AdminRepository;
import com.chiliclub.chilichat.repository.ChatRoomRepository;
import com.chiliclub.chilichat.repository.UserChatRoomRepository;
import com.chiliclub.chilichat.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ChatRoomServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserChatRoomRepository userChatRoomRepository;

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @InjectMocks
    private ChatRoomService chatRoomService;

    @Test
    @DisplayName("운영자가 아닌 채팅방을 삭제 시도하면 RequestForbiddenException")
    void removeChatRoomFailTest() {
        // given
        Long currentUserNo = 1L;
        Long chatRoomNo = 1L;

        given(userService.getCurrentUserNo())
                .willReturn(currentUserNo);

        given(adminRepository.findByUserNoAndChatRoomNo(any(Long.class), any(Long.class)))
                .willReturn(Optional.empty());

        // when & then
        assertThrows(RequestForbiddenException.class, () -> chatRoomService.removeChatRoom(chatRoomNo));
    }

    @Test
    @DisplayName("운영자가 아닌 채팅방을 수정 시도하면 RequestForbiddenException")
    void modifyChatRoomFailTest() {
        // given
        Long chatRoomNo = 1L;
        Long currentUserNo = 1L;

        ChatRoomUpdateRequest chatRoomUpdateRequest = ChatRoomUpdateRequest.builder()
                .title("채팅방 수정")
                .build();

        given(chatRoomRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(ChatRoomEntity.builder()
                        .title("채팅방")
                        .build()));

        given(userService.getCurrentUserNo())
                .willReturn(currentUserNo);

        given(adminRepository.findByUserNoAndChatRoomNo(any(Long.class), any(Long.class)))
                .willReturn(Optional.empty());

        // when & then
        assertThrows(RequestForbiddenException.class, () -> chatRoomService.modifyChatRoom(chatRoomNo, chatRoomUpdateRequest));
    }

    @Test
    @DisplayName("수정하려는 채팅방이 존재하지 않는다면 ResourceNotFoundException")
    void modifyChatRoomFailTest2() {
        // given
        Long chatRoomNo = 1L;

        ChatRoomUpdateRequest chatRoomUpdateRequest = ChatRoomUpdateRequest.builder()
                .title("채팅방 수정")
                .build();

        given(chatRoomRepository.findById(any(Long.class)))
                .willReturn(Optional.empty());

        // when & then
        assertThrows(ResourceNotFoundException.class, () -> chatRoomService.modifyChatRoom(chatRoomNo, chatRoomUpdateRequest));
    }

    @Test
    @DisplayName("채팅방 조회 테스트")
    void test_findChatRoomList2() {

        // given
        UserEntity user1 = mock(UserEntity.class);
        UserEntity user2 = mock(UserEntity.class);
        UserEntity user3 = mock(UserEntity.class);

        AdminEntity admin1 = AdminEntity.builder()
                .user(user1)
                .build();
        AdminEntity admin2 = AdminEntity.builder()
                .user(user2)
                .build();

        ChatRoomEntity chatRoomEntity1 = ChatRoomEntity.builder()
                .title("room1")
                .admin(admin1)
                .build();
        ChatRoomEntity chatRoomEntity2 = ChatRoomEntity.builder()
                .title("room2")
                .admin(admin2)
                .build();
        ReflectionTestUtils.setField(chatRoomEntity1, "no", 1L);
        ReflectionTestUtils.setField(chatRoomEntity2, "no", 2L);

        List<ChatRoomEntity> chatRoomEntities = List.of(
                chatRoomEntity1,
                chatRoomEntity2
        );

        UserChatRoomEntity userChatRoomEntity1 = UserChatRoomEntity.builder()
                .user(user1)
                .chatRoom(chatRoomEntity1)
                .build();
        UserChatRoomEntity userChatRoomEntity2 = UserChatRoomEntity.builder()
                .user(user2)
                .chatRoom(chatRoomEntity2)
                .build();
        UserChatRoomEntity userChatRoomEntity3 = UserChatRoomEntity.builder()
                .user(user3)
                .chatRoom(chatRoomEntity2)
                .build();

        List<UserChatRoomEntity> userChatRoomEntities = List.of(
                userChatRoomEntity1,
                userChatRoomEntity2,
                userChatRoomEntity3
        );

        given(user1.getNo()).willReturn(10L);
        given(user2.getNo()).willReturn(20L);
        given(chatRoomRepository.fetchAll()).willReturn(chatRoomEntities);
        given(userChatRoomRepository.findAll()).willReturn(userChatRoomEntities);

        // when
        List<ChatRoomFindResponse> result = chatRoomService.findChatRoomList2();

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getAdminUserNo()).isEqualTo(10L);
        assertThat(result.get(0).getTotalParticipantCount()).isEqualTo(1);
        assertThat(result.get(1).getAdminUserNo()).isEqualTo(20L);
        assertThat(result.get(1).getTotalParticipantCount()).isEqualTo(2);
    }
}
