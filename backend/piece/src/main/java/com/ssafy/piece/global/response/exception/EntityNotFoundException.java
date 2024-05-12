package com.ssafy.piece.global.response.exception;

import com.ssafy.piece.global.response.code.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
