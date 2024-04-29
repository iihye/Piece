package com.ssafy.user.exception;

import com.ssafy.user.global.response.code.ErrorCode;
import com.ssafy.user.global.response.exception.BusinessException;

public class DuplicatedEmailException extends BusinessException {

    public DuplicatedEmailException() {
        super(ErrorCode.DUPLICATED_EMAIL);
    }
}
