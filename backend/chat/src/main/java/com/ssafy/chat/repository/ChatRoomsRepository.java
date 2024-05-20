package com.ssafy.chat.repository;

import com.ssafy.chat.entity.ChatRooms;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomsRepository extends JpaRepository<ChatRooms, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE ChatRooms c SET c.isOpened = false WHERE c.chatRoomId = :chatRoomId")
    void updateChatRoomStatus(Long chatRoomId);

    @Query("SELECT c.isOpened FROM ChatRooms c WHERE c.chatRoomId = :chatRoomId")
    Boolean findIsOpenedByChatRoomId(Long chatRoomId);

    @Query("SELECT c.isPersonal FROM ChatRooms c WHERE c.chatRoomId = :chatRoomId")
    Boolean findIsPersonalByChatRoomId(Long chatRoomId);

    @Query("SELECT cr.chatRoomId FROM ChatRooms cr WHERE cr.isPersonal = true " +
        "AND cr.chatRoomId IN " +
        "(SELECT p1.participantsId.chatroomId FROM Participants p1 WHERE p1.participantsId.userId = :userId) " +
        "AND cr.chatRoomId IN " +
        "(SELECT p2.participantsId.chatroomId FROM Participants p2 WHERE p2.participantsId.userId = :partnerId)")
    Long existsPersonalChatRoomByParticipants(@Param("userId") Long userId, @Param("partnerId") Long partnerId);

    Boolean existsByCultureId(Long cultureId);

    Optional<ChatRooms> findChatRoomsByCultureId(@Param("cultureId") Long cultureId);
}