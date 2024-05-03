package com.ssafy.piece.domain.users.exception;

import com.ssafy.piece.global.response.code.ErrorCode;
import com.ssafy.piece.global.response.code.ResponseCode;
import com.ssafy.piece.global.response.exception.BusinessException;

public class DuplicatedEmailException extends BusinessException {

    public DuplicatedEmailException() {
        super(ErrorCode.DUPLICATED_EMAIL);
    }
}
