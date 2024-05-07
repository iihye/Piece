package com.ssafy.piece.domain.users.exception;

import com.ssafy.piece.global.response.code.ErrorCode;
import com.ssafy.piece.global.response.code.ResponseCode;
import com.ssafy.piece.global.response.exception.BusinessException;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException() { //유저정보를 찾을 수 없습니다.
        super(ErrorCode.USER_NOT_FOUND);
    }
}
