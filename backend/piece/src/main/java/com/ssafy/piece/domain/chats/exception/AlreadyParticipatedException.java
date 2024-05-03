package com.ssafy.piece.domain.chats.exception;

import com.ssafy.piece.global.response.code.ErrorCode;
import com.ssafy.piece.global.response.exception.BusinessException;

public class AlreadyParticipatedException extends BusinessException {

    public AlreadyParticipatedException() {
        super(ErrorCode.INVALID_REQUEST);
    }
}
