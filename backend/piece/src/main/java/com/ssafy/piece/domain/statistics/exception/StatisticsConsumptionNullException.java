package com.ssafy.piece.domain.statistics.exception;

import com.ssafy.piece.global.response.code.ErrorCode;
import com.ssafy.piece.global.response.exception.BusinessException;

public class StatisticsConsumptionNullException extends BusinessException {

    public StatisticsConsumptionNullException() { super(ErrorCode.STATISTICS_CONSUMPTION_NULL);}

}
