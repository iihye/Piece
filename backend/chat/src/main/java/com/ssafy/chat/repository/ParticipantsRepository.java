package com.ssafy.chat.repository;

import com.ssafy.chat.entity.Participants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantsRepository extends JpaRepository<Participants, Long> {

}
