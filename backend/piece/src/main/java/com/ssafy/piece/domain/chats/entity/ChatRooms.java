package com.ssafy.piece.domain.chats.entity;

import com.ssafy.piece.domain.cultures.entity.Cultures;
import com.ssafy.piece.global.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

    @OneToOne
    @JoinColumn(name = "culture_id", nullable = true)
    private Cultures culture;

    @Column(name = "chatroom_name", nullable = false)
    private String chatRoomName;

    @Column(name = "is_opened", nullable = false)
    private Boolean isOpened;

    @Column(name = "is_personal", nullable = false)
    private Boolean isPersonal;
}