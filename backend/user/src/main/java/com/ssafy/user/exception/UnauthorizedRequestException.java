package com.ssafy.user.exception;

import com.ssafy.user.global.response.code.ErrorCode;
import com.ssafy.user.global.response.exception.BusinessException;

public class UnauthorizedRequestException extends BusinessException {

    public UnauthorizedRequestException() {
        super(ErrorCode.UNAUTHORIZED_REQUEST);
    }
}
