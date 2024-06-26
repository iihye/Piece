package com.ssafy.chat.entity;

import com.ssafy.piece.global.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name = "chatrooms")
public class ChatRooms extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long chatRoomId;

    @Column(name = "culture_id")
    private Long cultureId;

    @Column(name = "chatroom_name", nullable = false)
    private String chatRoomName;

    @Column(name = "is_opened", nullable = false)
    private Boolean isOpened;

    @Column(name = "is_personal", nullable = false)
    private Boolean isPersonal;
}