package com.ssafy.piece.domain.cultures.exception;

import com.ssafy.piece.global.response.code.ErrorCode;
import com.ssafy.piece.global.response.exception.BusinessException;

public class FetchImageErrorException extends BusinessException {

    public FetchImageErrorException() {
        super(ErrorCode.FETCH_IMAGE_ERROR);
    }
}
