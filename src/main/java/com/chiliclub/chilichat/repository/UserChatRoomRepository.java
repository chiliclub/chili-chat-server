package com.chiliclub.chilichat.repository;

import com.chiliclub.chilichat.entity.UserChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoomEntity, Long> {
}
