package com.ssafy.piece.global.response.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ResponseCode {
    /**
     * User
     */
    LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다."),

    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃에 성공했습니다."),

    CHECK_EMAIL_GOOD(HttpStatus.OK, "사용 가능한 이메일입니다"),

    CHECK_EMAIL_BAD(HttpStatus.OK, "이미 사용 중인 이메일입니다"),

    JOIN_SUCCESS(HttpStatus.CREATED, "회원가입에 성공했습니다."),

    CHECK_PASSWORD_SUCCESS(HttpStatus.OK, "비밀번호 확인에 성공했습니다."),

    PASSWORD_UPDATE_SUCCESS(HttpStatus.OK, "비밀번호 변경에 성공했습니다."),

    // Chat
    CHATROOM_CREATED(HttpStatus.CREATED, "채팅방이 생성되었습니다."),
    CHATROOM_DELETED(HttpStatus.OK, "채팅방이 삭제되었습니다."),
    CHATMESSAGE_LIST_SUCCESS(HttpStatus.OK, "채팅메세지 목록을 조회했습니다."),
    CHATMESSAGE_N_LIST_SUCCESS(HttpStatus.OK, "채팅메세지 목록 n개를 조회했습니다."),
    PARTICIPATE_SUCCESS(HttpStatus.CREATED, "채팅방에 참여했습니다."),
    PARTICIPATED_LIST_SUCCESS(HttpStatus.OK, "참여찬 채팅방 목록을 조회했습니다."),
    LEAVE_CHATROOM_SUCCESS(HttpStatus.OK, "채팅방에서 퇴장했습니다."),
    PARTICIPANT_COUNT_SUCCESS(HttpStatus.OK, "채팅방 참여 인원 수를 조회했습니다.");

    private final HttpStatus httpStatus;

    private final String message;
}
