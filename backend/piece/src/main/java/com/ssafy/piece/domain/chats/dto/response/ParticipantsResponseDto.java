package com.ssafy.piece.domain.chats.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParticipantsResponseDto {

    Long chatRoomId;
    Long userId;
    LocalDateTime createdAt;
}