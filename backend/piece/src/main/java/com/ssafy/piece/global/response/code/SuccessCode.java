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
    FIND_MY_PIECE_LIST_NULL_SUCCESS(HttpStatus.OK, "내 조각 리스트 조회에 성공했습니다."),
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

    // cultures(cake)
    FIND_TMDB_CULTURE_SUCCESS(HttpStatus.OK, "TMDB 케이크 조회에 성공했습니다."),
    FIND_KOPIS_CULTURE_SUCCESS(HttpStatus.OK, "KOPIS 케이크 조회에 성공했습니다."),
    ADD_CULTURE_HEART_SUCCESS(HttpStatus.OK, "케이크 좋아요에 성공했습니다."),
    REMOVE_CULTURE_HEART_SUCCESS(HttpStatus.OK, "케이크 삭제에 성공했습니다."),
    FIND_CULTURE_SUCCESS(HttpStatus.OK, "케이크 조회에 성공했습니다."),
    COUNT_CULTURE_HEART_SUCCESS(HttpStatus.OK, "케이크 좋아요 개수 조회에 성공했습니다."),


    // 검색
    SEARCH_PIECE_SUCCESS(HttpStatus.OK,"조각 검색에 성공했습니다."),
    SEARCH_MY_PIECE_SUCCESS(HttpStatus.OK,"내 조각 검색에 성공했습니다."),

    FETCH_IMAGE_SUCCESS(HttpStatus.OK, "이미지 변환에 성공하였습니다."),


    /**
     * s3
     */
    GET_PRESIGNEDURL_SUCCESS(HttpStatus.OK, "presigned url을 받았습니다."),
    DOWNLOAD_IMAGE_SUCCESS(HttpStatus.OK, "이미지 다운로드에 성공했습니다."),
    DELETE_IMAGE_SUCCESS(HttpStatus.OK, "이미지 삭제에 성공했습니다."),

    // 통계
    FIND_VIEW_STATISTICS_SUCCESS(HttpStatus.OK, "관람수 통계 조회에 성공했습니다."),
    FIND_CONSUMPTIONS_SUCCESS(HttpStatus.OK, "소비 통계 조회에 성공했습니다."),

    // 뒷면 이미지 생성
    CREATE_IMAGE_SUCCESS(HttpStatus.OK, "뒷면 이미지 생성에 성공했습니다.");

    private final HttpStatus httpStatus;

    private final String message;
}
