package com.chiliclub.chilichat.repository;

import com.chiliclub.chilichat.model.ChatRoomFindResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public List<ChatRoomFindResponse> findChatRoomListWithAdminAndParticipantCount() {
        String query = "SELECT c_n.title AS title, c_n.ins_datetime AS insDatetime, c_n.upd_datetime AS updDatetime, a.user_no AS adminUserNo, u_c_n.chat_room_count AS totalParticipantCount " +
                "FROM chat_room AS c_n " +
                "LEFT JOIN admin AS a ON c_n.chat_room_no = a.chat_room_no " +
                "LEFT JOIN " +
                "(SELECT user_chat_room.chat_room_no, count(user_chat_room.user_chat_room_no) AS chat_room_count FROM user_chat_room GROUP BY chat_room_no) " +
                "AS u_c_n ON u_c_n.chat_room_no = c_n.chat_room_no";

        JpaResultMapper resultMapper = new JpaResultMapper();
        Query nativeQuery = em.createNativeQuery(query);

        return resultMapper.list(nativeQuery, ChatRoomFindResponse.class);
    }
}
