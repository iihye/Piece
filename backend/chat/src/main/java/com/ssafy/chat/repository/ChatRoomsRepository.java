package com.ssafy.chat.repository;

import com.ssafy.chat.entity.ChatRooms;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomsRepository extends JpaRepository<ChatRooms, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE ChatRooms c SET c.isOpened = false WHERE c.chatRoomId = :chatRoomId")
    void updateChatRoomStatus(Long chatRoomId);

    @Query("SELECT c.isOpened FROM ChatRooms c WHERE c.chatRoomId = :chatRoomId")
    Boolean findIsOpenedByChatRoomId(Long chatRoomId);

    Optional<ChatRooms> findByCultureIdAndIsPersonal(Long cultureId, Boolean isPersonal);
}