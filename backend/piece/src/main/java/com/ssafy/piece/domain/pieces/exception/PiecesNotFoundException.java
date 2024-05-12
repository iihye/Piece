package com.ssafy.piece.domain.pieces.exception;

import com.ssafy.piece.global.response.code.ErrorCode;
import com.ssafy.piece.global.response.exception.BusinessException;

public class PiecesNotFoundException extends BusinessException {

    public PiecesNotFoundException() {
        super(ErrorCode.PIECES_NOT_FOUND);
    }

}
