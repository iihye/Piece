package com.ssafy.chat.exception;

import com.ssafy.chat.global.response.code.ErrorCode;
import com.ssafy.chat.global.response.exception.BusinessException;

public class AlreadyCreatedChatRoomException extends BusinessException {

    public AlreadyCreatedChatRoomException() {
        super(ErrorCode.INVALID_REQUEST);
    }
}
