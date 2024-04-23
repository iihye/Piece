package com.ssafy.piece.domain.chats.repository;

import com.ssafy.piece.domain.chats.entity.ChatRooms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomsRepository extends JpaRepository<ChatRooms, Long> {

}
