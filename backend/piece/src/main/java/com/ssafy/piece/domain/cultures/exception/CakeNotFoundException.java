package com.ssafy.piece.domain.cultures.exception;

import com.ssafy.piece.global.response.code.ErrorCode;
import com.ssafy.piece.global.response.exception.BusinessException;

public class CakeNotFoundException extends BusinessException {

    public CakeNotFoundException() {
        super(ErrorCode.CAKE_NOT_FOUND);
    }
}
