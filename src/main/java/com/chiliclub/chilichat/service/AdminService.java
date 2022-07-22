package com.chiliclub.chilichat.service;

import com.chiliclub.chilichat.common.exception.ResourceNotFoundException;
import com.chiliclub.chilichat.entity.AdminEntity;
import com.chiliclub.chilichat.entity.ChatRoomEntity;
import com.chiliclub.chilichat.repository.AdminRepository;
import com.chiliclub.chilichat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final ChatRoomRepository chatRoomRepository;

    public AdminEntity findAdmin(Long userNo) {

        return adminRepository.findByUserNo(userNo)
                .orElseThrow(() -> new ResourceNotFoundException("해당 user_no를 가진 방장이 존재하지 않습니다"));
    }

    @Transactional(readOnly = true)
    public boolean checkIsAdmin(Long userNo, Long chatRoomNo) {

        AdminEntity adminEntity = findAdmin(userNo);
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(chatRoomNo)
                .orElseThrow(() -> new ResourceNotFoundException("해당 chat_room_no를 지닌 채팅방이 존재하지 않습니다"));

        return adminEntity.getChatRoom() == chatRoomEntity;
    }
}
