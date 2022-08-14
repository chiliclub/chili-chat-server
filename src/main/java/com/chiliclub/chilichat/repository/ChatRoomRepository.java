package com.chiliclub.chilichat.repository;

import com.chiliclub.chilichat.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

//    @Query(value = "SELECT c_n.title AS title, " +
//            "c_n.ins_datetime AS ins_datetime, " +
//            "c_n.upd_datetime AS upd_datetime, " +
//            "a.user_no AS admin_user_no, " +
//            "u_c_n.chat_room_count AS total_participant_count " +
//            "FROM chat_room AS c_n " +
//            "LEFT JOIN admin AS a ON c_n.chat_room_no = a.chat_room_no " +
//            "LEFT JOIN (SELECT user_chat_room.chat_room_no, count(user_chat_room.user_chat_room_no) AS chat_room_count " +
//            "FROM user_chat_room GROUP BY chat_room_no) AS u_c_n ON u_c_n.chat_room_no = c_n.chat_room_no",
//            nativeQuery = true
//    )
//    List<ChatRoomFindResponse> fetchAll();

    @Query("select c from ChatRoomEntity c join fetch c.admin a join fetch a.user")
    List<ChatRoomEntity> fetchAll();
}
