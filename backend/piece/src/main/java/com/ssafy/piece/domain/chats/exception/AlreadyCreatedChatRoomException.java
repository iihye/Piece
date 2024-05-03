package com.ssafy.piece.domain.chats.exception;

import com.ssafy.piece.global.response.code.ErrorCode;
import com.ssafy.piece.global.response.exception.BusinessException;

public class AlreadyCreatedChatRoomException extends BusinessException {

    public AlreadyCreatedChatRoomException() {
        super(ErrorCode.INVALID_REQUEST);
    }
}
