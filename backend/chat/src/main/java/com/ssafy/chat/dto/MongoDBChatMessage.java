package com.ssafy.chat.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class MongoDBChatMessage {

    Long chatRoomId;

    String content;

    Date createdAt;
}
