package com.ssafy.user.exception;

import com.ssafy.user.global.response.code.ErrorCode;
import com.ssafy.user.global.response.exception.BusinessException;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(ErrorCode userNotFound) { //유저정보를 찾을 수 없습니다.
        super(ErrorCode.USER_NOT_FOUND);
    }
}
