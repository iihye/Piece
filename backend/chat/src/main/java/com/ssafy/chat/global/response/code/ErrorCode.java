package com.ssafy.chat.global.response.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ResponseCode {

    /**
     * Common
     */
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "유효하지 않은 파라미터입니다."),

    UNAUTHORIZED_REQUEST(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),

    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "권한이 존재하지 않는 사용자입니다."),

    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "리소스가 존재하지 않습니다."),

    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 METHOD 요청입니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 오류가 발생했습니다."),

    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    /**
     * User
     */
    DUPLICATED_USER(HttpStatus.CONFLICT, "이미 가입된 유저 정보입니다."),

    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "중복된 닉네임입니다."),

    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이미 해당 이메일로 가입된 정보가 존재합니다.."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저의 정보를 찾을 수 없습니다."),

    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "잘못된 패스워드입니다."),

    ACCOUNT_MISMATCH(HttpStatus.UNAUTHORIZED, "계정 정보가 일치하지 않습니다."),

    DUPLICATED_PASSWORD(HttpStatus.CONFLICT, "현재 패스워드와 같습니다."),

    UPDATE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "이미 승인된 사용자입니다."),

    BLOCK_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "이미 정지된 사용자입니다."),

    // 조각
    PIECES_NOT_FOUND(HttpStatus.NOT_FOUND, "조각을 찾을 수 없습니다."),
    PIECES_RECENT_NOT_FOUND(HttpStatus.NOT_FOUND, "1년 전 조각이 존재하지 않습니다."),

    // 기록
    RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "기록을 찾을 수 없습니다."),

    REQUEST_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "요청에 실패했습니다."),

    /**
     * s3
     */
    UPLOAD_FAIL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다.");

    private final HttpStatus httpStatus;

    private final String message;
}
