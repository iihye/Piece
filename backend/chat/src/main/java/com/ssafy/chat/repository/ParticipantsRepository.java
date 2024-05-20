package com.ssafy.chat.repository;

import com.ssafy.chat.entity.ChatRooms;
import com.ssafy.chat.entity.Participants;
import com.ssafy.chat.entity.ParticipantsId;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParticipantsRepository extends JpaRepository<Participants, ParticipantsId> {
    @Query("SELECT c FROM Participants p " +
        "INNER JOIN ChatRooms c ON p.participantsId.chatroomId = c.chatRoomId " +
        "WHERE p.participantsId.userId = :userId AND c.isPersonal = :isPersonal")
    List<ChatRooms> findIsPersonalChatRoomsByUserId(Long userId, Boolean isPersonal);

    @Query("SELECT p.participantsId.userId FROM Participants p WHERE p.participantsId.chatroomId = :chatroomId")
    List<Long> findUserIdsByChatroomId(@Param("chatroomId") Long chatroomId);

    Long countByParticipantsId_ChatroomId(Long chatroomId);

    @Transactional
    void deleteByParticipantsId_ChatroomId(Long chatroomId);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Participants p WHERE p.participantsId.userId = :userId AND p.participantsId.chatroomId = :chatroomId")
    Boolean existsByUserIdAndChatroomId(@Param("userId") Long userId, @Param("chatroomId") Long chatroomId);
}
