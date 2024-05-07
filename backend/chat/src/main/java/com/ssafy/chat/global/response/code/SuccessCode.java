package com.ssafy.chat.global.response.code;

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

    //아래 두개 추가하였습니다.(닉네임 중복확인용)
    CHECK_NICKNAME_GOOD(HttpStatus.OK, "사용 가능한 닉네임입니다."),
    CHECK_NICKNAME_BAD(HttpStatus.OK, "이미 사용 중인 닉네임입니다."),

    CHECK_EMAIL_GOOD(HttpStatus.OK, "사용 가능한 이메일입니다"),

    CHECK_EMAIL_BAD(HttpStatus.OK, "이미 사용 중인 이메일입니다"),

    JOIN_SUCCESS(HttpStatus.CREATED, "회원가입에 성공했습니다."),
    NICKNAME_UPDATE_SUCCESS(HttpStatus.OK, "닉네임 변경에 성공했습니다."),

    CHECK_PASSWORD_SUCCESS(HttpStatus.OK, "비밀번호 확인에 성공했습니다."),

    PASSWORD_UPDATE_SUCCESS(HttpStatus.OK, "비밀번호 변경에 성공했습니다."),

    // 조각
    ADD_PIECE_SUCCESS(HttpStatus.OK, "조각 생성에 성공했습니다."),
    FIND_PIECE_SUCCESS(HttpStatus.OK, "조각 조회에 성공했습니다."),
    DELETE_PIECE_SUCCESS(HttpStatus.OK, "조각 삭제에 성공했습니다."),
    FIND_PIECE_RECENT_SUCCESS(HttpStatus.OK, "1년 전 조각 조회에 성공했습니다."),

    // 찜
    ADD_HEART_SUCCESS(HttpStatus.OK, "찜 등록에 성공했습니다."),
    DELETE_HEART_SUCCESS(HttpStatus.OK, "찜 삭제에 성공했습니다."),
    IS_HEART_SUCCESS(HttpStatus.OK, "찜 여부 확인에 성공했습니다."),

    // 기록
    ADD_RECORD_SUCCESS(HttpStatus.OK, "기록 생성에 성공했습니다."),
    FIND_RECORD_SUCCESS(HttpStatus.OK, "기록 조회에 성공했습니다."),
    UPDATE_RECORD_SUCCESS(HttpStatus.OK, "기록 수정에 성공했습니다."),

    // 조각 리스트
    FIND_PIECE_LIST_SUCCESS(HttpStatus.OK, "조각 리스트 조회에 성공했습니다."),
    FIND_MY_PIECE_LIST_SUCCESS(HttpStatus.OK, "내 조각 리스트 조회에 성공했습니다."),
    FIND_HEART_PIECE_LIST_SUCCESS(HttpStatus.OK, "찜한 조각 리스트 조회에 성공했습니다."),
    FIND_RECENT_PIECE_LIST_SUCCESS(HttpStatus.OK, "최근 조각 리스트 조회에 성공했습니다."),

    // 칭호
    ADD_LABEL_SUCCESS(HttpStatus.OK, "칭호 생성에 성공했습니다."),
    FIND_LABEL_SUCCESS(HttpStatus.OK, "칭호 조회에 성공했습니다."),
    ADD_MY_LABELS_SUCCESS(HttpStatus.OK, "칭호 착용에 성공했습니다."),
    DELETE_MY_LABELS_SUCCESS(HttpStatus.OK, "칭호 착용 해제에 성공했습니다."),
    FIND_MY_LABELS_SUCCESS(HttpStatus.OK, "내 칭호 목록 조회에 성공했습니다."),
    CHECK_MY_LABELS_SUCCESS(HttpStatus.OK, "칭호 획득 검사에 성공했습니다."),

    REQUEST_SUCCESS(HttpStatus.OK, "요청에 성공했습니다."),

    // Chat
    CHATROOM_CREATED(HttpStatus.CREATED, "채팅방이 생성되었습니다."),
    CHATROOM_DELETED(HttpStatus.OK, "채팅방이 삭제되었습니다."),
    CHATMESSAGE_LIST_SUCCESS(HttpStatus.OK, "채팅메세지 목록을 조회했습니다."),
    CHATMESSAGE_N_LIST_SUCCESS(HttpStatus.OK, "채팅메세지 목록 n개를 조회했습니다."),
    PARTICIPATE_SUCCESS(HttpStatus.CREATED, "채팅방에 참여했습니다."),
    PARTICIPATED_LIST_SUCCESS(HttpStatus.OK, "참여찬 채팅방 목록을 조회했습니다."),
    LEAVE_CHATROOM_SUCCESS(HttpStatus.OK, "채팅방에서 퇴장했습니다."),
    PARTICIPANT_COUNT_SUCCESS(HttpStatus.OK, "채팅방 참여 인원 수를 조회했습니다."),

    /**
     * s3
     */
    GET_PRESIGNEDURL_SUCCESS(HttpStatus.OK, "presigned url을 받았습니다.");

    private final HttpStatus httpStatus;

    private final String message;
}
