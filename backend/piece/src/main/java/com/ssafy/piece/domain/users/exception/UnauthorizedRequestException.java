package com.ssafy.piece.domain.users.exception;

import com.ssafy.piece.global.response.code.ErrorCode;
import com.ssafy.piece.global.response.code.ResponseCode;
import com.ssafy.piece.global.response.exception.BusinessException;
import org.aspectj.apache.bcel.classfile.Code;

public class UnauthorizedRequestException extends BusinessException {

    public UnauthorizedRequestException() {
        super(ErrorCode.UNAUTHORIZED_REQUEST);
    }
}
