package com.chiliclub.chilichat.service;

import com.chiliclub.chilichat.common.exception.RequestForbiddenException;
import com.chiliclub.chilichat.common.exception.ResourceNotFoundException;
import com.chiliclub.chilichat.entity.ChatRoomEntity;
import com.chiliclub.chilichat.model.ChatRoomUpdateRequest;
import com.chiliclub.chilichat.repository.AdminRepository;
import com.chiliclub.chilichat.repository.ChatRoomRepository;
import com.chiliclub.chilichat.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ChatRoomServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private UserService userService;

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
}
