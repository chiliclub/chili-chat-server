package com.chiliclub.chilichat.service;

import com.chiliclub.chilichat.common.exception.RequestForbiddenException;
import com.chiliclub.chilichat.common.exception.ResourceNotFoundException;
import com.chiliclub.chilichat.entity.AdminEntity;
import com.chiliclub.chilichat.entity.ChatRoomEntity;
import com.chiliclub.chilichat.entity.UserChatRoomEntity;
import com.chiliclub.chilichat.entity.UserEntity;
import com.chiliclub.chilichat.model.ChatRoomCreateRequest;
import com.chiliclub.chilichat.model.ChatRoomFindResponse;
import com.chiliclub.chilichat.model.ChatRoomUpdateRequest;
import com.chiliclub.chilichat.model.ChatRoomUpdateResponse;
import com.chiliclub.chilichat.repository.*;
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

    private final UserChatRoomRepository userChatRoomRepository;

    private final CustomJpaRepository customJpaRepository;

    @Transactional
    public Long addChatRoom(ChatRoomCreateRequest chatRoomCreateRequest) {

        ChatRoomEntity chatRoom = ChatRoomEntity.create(chatRoomCreateRequest);
        UserEntity currentUser = userRepository.findByNo(userService.getCurrentUserNo());

        // 현재 사용자를 Admin으로 생성
        adminRepository.save(AdminEntity.create(currentUser, chatRoom));

        return chatRoomRepository.save(ChatRoomEntity.create(chatRoomCreateRequest)).getNo();
    }

    public List<ChatRoomFindResponse> findChatRoomList() {
        return customJpaRepository.findChatRoomListWithAdminAndParticipantCount();
    }

    public List<ChatRoomFindResponse> findChatRoomList2() {

        List<ChatRoomEntity> chatRoomEntities = chatRoomRepository.fetchAll();
        List<UserChatRoomEntity> userChatRoomEntities = userChatRoomRepository.findAll();

        return chatRoomEntities.stream().map(chatRoomEntity -> {
            // TODO: 최적화
            // chatRoomEntity의 chatRoomNo이 동일한 userChatRoomEntity의 개수를 셈
            long totalParticipantCount = userChatRoomEntities.stream().filter(userChatRoomEntity ->
                    userChatRoomEntity.getChatRoom().getNo().equals(chatRoomEntity.getNo())
            ).count();

            return ChatRoomFindResponse.builder()
                    .chatRoomNo(chatRoomEntity.getNo())
                    .title(chatRoomEntity.getTitle())
                    .insDatetime(chatRoomEntity.getInsDatetime())
                    .updDatetime(chatRoomEntity.getUpdDatetime())
                    .adminUserNo(chatRoomEntity.getAdmin().getUser().getNo())
                    .totalParticipantCount((int) totalParticipantCount)
                    .build();
        }).collect(Collectors.toList());
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
