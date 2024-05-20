package com.ssafy.chat.dto.response;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageResponseDto {

    Long chatRoomId;

    Long senderId;

    String content;

    Date createdAt;

    String profileImage;

    String nickname;

    String title;
}
