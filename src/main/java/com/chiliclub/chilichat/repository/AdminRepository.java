package com.chiliclub.chilichat.repository;

import com.chiliclub.chilichat.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    @Query("SELECT a from AdminEntity a WHERE a.user.no = :userNo")
    Optional<AdminEntity> findByUserNo(@Param("userNo") Long userNo);

    @Query("SELECT a from AdminEntity a WHERE a.user.no = :userNo and a.chatRoom.no = :chatRoomNo")
    Optional<AdminEntity> findByUserNoAndChatRoomNo(@Param("userNo") Long userNo, @Param("chatRoomNo") Long chatRoomNo);
}
