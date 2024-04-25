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

    // 조각
    ADD_PIECE_SUCCESS(HttpStatus.OK, "조각 생성에 성공했습니다."),
    FIND_PIECE_SUCCESS(HttpStatus.OK, "조각 조회에 성공했습니다."),
    DELETE_PIECE_SUCCESS(HttpStatus.OK, "조각 삭제에 성공했습니다."),
    FIND_PIECE_RECENT_SUCCESS(HttpStatus.OK, "1년 전 조각 조회에 성공했습니다."),

    // 찜
    ADD_HEART_SUCCESS(HttpStatus.OK, "찜 등록에 성공했습니다."),
    DELETE_HEART_SUCCESS(HttpStatus.OK, "찜 삭제에 성공했습니다."),

    // 기록
    ADD_RECORD_SUCCESS(HttpStatus.OK, "기록 생성에 성공했습니다."),
    FIND_RECORD_SUCCESS(HttpStatus.OK, "기록 조회에 성공했습니다."),
    UPDATE_RECORD_SUCCESS(HttpStatus.OK, "기록 수정에 성공했습니다."),

    // 조각 리스트
    FIND_PIECE_LIST_SUCCESS(HttpStatus.OK, "조각 리스트 조회에 성공했습니다."),
    FIND_MY_PIECE_LIST_SUCCESS(HttpStatus.OK, "내 조각 리스트 조회에 성공했습니다."),
    FIND_RECENT_PIECE_LIST_SUCCESS(HttpStatus.OK, "최근 조각 리스트 조회에 성공했습니다."),

    REQUEST_SUCESS(HttpStatus.OK, "요청에 성공했습니다.");

    private final HttpStatus httpStatus;

    private final String message;
}
