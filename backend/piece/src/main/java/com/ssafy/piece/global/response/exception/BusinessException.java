package com.ssafy.piece.global.response.exception;

import com.ssafy.piece.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {

    private final ResponseCode responseCode;

}

