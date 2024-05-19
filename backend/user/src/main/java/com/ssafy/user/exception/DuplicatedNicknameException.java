package com.ssafy.user.exception;

import com.ssafy.user.global.response.code.ErrorCode;
import com.ssafy.user.global.response.exception.BusinessException;

public class DuplicatedNicknameException extends BusinessException {

    public DuplicatedNicknameException() {
        super(ErrorCode.DUPLICATED_NICKNAME);
    }
}
