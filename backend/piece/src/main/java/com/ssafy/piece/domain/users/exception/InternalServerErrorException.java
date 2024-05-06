package com.ssafy.piece.domain.users.exception;

import com.ssafy.piece.global.response.code.ErrorCode;
import com.ssafy.piece.global.response.code.ResponseCode;
import com.ssafy.piece.global.response.exception.BusinessException;

public class InternalServerErrorException extends BusinessException {

    public InternalServerErrorException() {
        super(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
