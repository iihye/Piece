package com.ssafy.piece.domain.statistics.exception;

import com.ssafy.piece.global.response.code.ErrorCode;
import com.ssafy.piece.global.response.exception.BusinessException;

public class StatisticsViewNullException extends BusinessException {

    public StatisticsViewNullException() {super(ErrorCode.STATISTICS_VIEW_NULL);}

}
