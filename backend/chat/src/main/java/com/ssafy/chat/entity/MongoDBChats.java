package com.ssafy.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "chatmessages")
public class MongoDBChats {

    @Id
    @Column(nullable = false)
    Long chatRoomId;

    @Column(nullable = false)
    Long senderId;

    @Column(nullable = false)
    String content;

    @Column(nullable = false)
    Date createdAt;

    @Column(nullable = false)
    String profileImage;

    @Column(nullable = false)
    String nickname;

    @Column(nullable = false)
    String title;
}