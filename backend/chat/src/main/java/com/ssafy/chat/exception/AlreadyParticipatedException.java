package com.ssafy.chat.exception;

import com.ssafy.chat.global.response.code.ErrorCode;
import com.ssafy.chat.global.response.exception.BusinessException;

public class AlreadyParticipatedException extends BusinessException {

    public AlreadyParticipatedException() {
        super(ErrorCode.INVALID_REQUEST);
    }
}
