package com.ssafy.piece.domain.pieces.exception;

import com.ssafy.piece.global.response.code.ErrorCode;
import com.ssafy.piece.global.response.exception.BusinessException;

public class RecordNotFoundException extends BusinessException {

    public RecordNotFoundException() {
        super(ErrorCode.RECORD_NOT_FOUND);
    }

}