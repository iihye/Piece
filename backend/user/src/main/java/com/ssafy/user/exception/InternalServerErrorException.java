package com.ssafy.user.exception;

import com.ssafy.user.global.response.code.ErrorCode;
import com.ssafy.user.global.response.exception.BusinessException;

public class InternalServerErrorException extends BusinessException {

    public InternalServerErrorException() {
        super(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
