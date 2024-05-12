package com.ssafy.piece.domain.chats.repository;

import com.ssafy.piece.domain.chats.entity.Participants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantsRepository extends JpaRepository<Participants, Long> {

}
