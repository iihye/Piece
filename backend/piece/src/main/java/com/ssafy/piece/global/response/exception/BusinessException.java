package com.ssafy.piece.global.response.exception;

import com.ssafy.piece.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusinessException extends RuntimeException {

    private final ResponseCode responseCode;

}

