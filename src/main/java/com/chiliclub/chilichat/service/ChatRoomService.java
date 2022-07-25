package com.chiliclub.chilichat.service;

import com.chiliclub.chilichat.common.exception.RequestForbiddenException;
import com.chiliclub.chilichat.common.exception.ResourceNotFoundException;
import com.chiliclub.chilichat.entity.AdminEntity;
import com.chiliclub.chilichat.entity.ChatRoomEntity;
import com.chiliclub.chilichat.entity.UserEntity;
import com.chiliclub.chilichat.model.ChatRoomCreateRequest;
import com.chiliclub.chilichat.model.ChatRoomFindResponse;
import com.chiliclub.chilichat.model.ChatRoomUpdateRequest;
import com.chiliclub.chilichat.model.ChatRoomUpdateResponse;
import com.chiliclub.chilichat.repository.AdminRepository;
import com.chiliclub.chilichat.repository.ChatRoomRepository;
import com.chiliclub.chilichat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    private final AdminRepository adminRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    @Transactional
    public Long addChatRoom(ChatRoomCreateRequest chatRoomCreateRequest) {

        ChatRoomEntity chatRoom = ChatRoomEntity.create(chatRoomCreateRequest);
        UserEntity currentUser = userRepository.findByNo(userService.getCurrentUserNo());

        // 현재 사용자를 Admin으로 생성
        adminRepository.save(AdminEntity.create(currentUser, chatRoom));

        return chatRoomRepository.save(ChatRoomEntity.create(chatRoomCreateRequest)).getNo();
    }

    public List<ChatRoomFindResponse> findChatRoomList() {
        return chatRoomRepository.findAll().stream()
                .map(ChatRoomFindResponse::from).collect(Collectors.toList());
    }

    @Transactional
    public void removeChatRoom(Long chatRoomNo) {

        checkUserIsAdminOfChatRoom(userService.getCurrentUserNo(), chatRoomNo);

        chatRoomRepository.deleteById(chatRoomNo);
    }

    @Transactional
    public ChatRoomUpdateResponse modifyChatRoom(Long chatRoomNo, ChatRoomUpdateRequest chatRoomUpdateRequest) {
        ChatRoomEntity chatRoomToModify = chatRoomRepository.findById(chatRoomNo)
                .orElseThrow(ResourceNotFoundException::new);

        checkUserIsAdminOfChatRoom(userService.getCurrentUserNo(), chatRoomNo);

        chatRoomToModify.update(chatRoomUpdateRequest);

        return ChatRoomUpdateResponse.from(chatRoomToModify);
    }

    /**
     * 사용자가 채팅방의 관리자인지 확인
     */
    private void checkUserIsAdminOfChatRoom(Long userNo, Long chatRoomNo) {
        adminRepository.findByUserNoAndChatRoomNo(userNo, chatRoomNo)
                .orElseThrow(() -> new RequestForbiddenException("채팅방에 대한 권한이 없습니다."));
    }
}
