package com.ssafy.chat.exception;

import com.ssafy.chat.global.response.code.ErrorCode;
import com.ssafy.chat.global.response.code.ResponseCode;
import com.ssafy.chat.global.response.exception.BusinessException;

public class ChatRoomNotFoundException extends BusinessException {
    public ChatRoomNotFoundException() {
        super(ErrorCode.CHATROOM_NOT_FOUND);
    }
}
