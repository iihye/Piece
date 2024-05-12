package com.ssafy.chat.dto.response;

import jakarta.persistence.Access;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.core.PriorityOrdered;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IsParticipateResponseDto {

    private Long chatRoomId;

    private CultureResponseDto culture;

    private List<ProcessedUserResponseDto> participants;

    private String chatRoomName;

    private Boolean isOpened;

    private Boolean isPersonal;

    private Long participantCount; // websocket session 관리로 추후 변경

    private String lastMessage; // websocket session 관리로 추후 변경
}
