package com.ssafy.piece.domain.pieces.exception;

import com.ssafy.piece.global.response.code.ErrorCode;
import com.ssafy.piece.global.response.exception.BusinessException;

public class PiecesRecentNotFoundException extends BusinessException {

    public PiecesRecentNotFoundException() {
        super(ErrorCode.PIECES_RECENT_NOT_FOUND);
    }
}
