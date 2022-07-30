package com.chiliclub.chilichat.repository;

import com.chiliclub.chilichat.entity.UserChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoomEntity, Long> {

    @Query("SELECT uc FROM UserChatRoomEntity uc JOIN FETCH uc.user WHERE uc.chatRoom.no = :chatRoomNo")
    List<UserChatRoomEntity> findByNo(@Param("chatRoomNo") Long chatRoomNo);
}
