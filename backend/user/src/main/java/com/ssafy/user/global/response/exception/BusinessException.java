package com.ssafy.user.global.response.exception;

import com.ssafy.user.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusinessException extends RuntimeException {

    private final ResponseCode responseCode;

}

