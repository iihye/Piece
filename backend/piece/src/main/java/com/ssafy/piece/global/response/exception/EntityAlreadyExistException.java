package com.ssafy.piece.global.response.exception;

import com.ssafy.piece.global.response.code.ErrorCode;

public class EntityAlreadyExistException extends BusinessException {

    public EntityAlreadyExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}