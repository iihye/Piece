package com.ssafy.chat.exception;

import com.ssafy.chat.global.response.code.ErrorCode;
import com.ssafy.chat.global.response.exception.BusinessException;
import lombok.Getter;

@Getter
public class AlreadyCreatedPersonalChatRoomException extends BusinessException {
    private final Long alreadyCreatedChatRoomId;
    public AlreadyCreatedPersonalChatRoomException(Long alreadyCreatedChatRoomId) {
        super(ErrorCode.INVALID_REQUEST);
        this.alreadyCreatedChatRoomId=alreadyCreatedChatRoomId;
    }
}
