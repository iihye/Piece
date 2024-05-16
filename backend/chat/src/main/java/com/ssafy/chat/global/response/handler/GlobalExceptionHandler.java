package com.ssafy.chat.global.response.handler;

import com.ssafy.chat.exception.AlreadyCreatedPersonalChatRoomException;
import com.ssafy.chat.global.response.code.ErrorCode;
import com.ssafy.chat.global.response.code.ResponseCode;
import com.ssafy.chat.global.response.exception.BusinessException;
import com.ssafy.chat.global.response.structure.ErrorResponse;
import jakarta.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * RestApiException
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleRestApiException(final BusinessException e) {
        logger.error("RestApiException caught: ", e);

        final ResponseCode responseCode = e.getResponseCode();
        return handleExceptionInternal(responseCode);
    }

    /**
     * IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(final IllegalArgumentException e) {
        logger.error("IllegalArgumentException caught: ", e);

        final ResponseCode responseCode = ErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(responseCode, responseCode.getMessage());
    }

    /**
     * MethodArgumentNotValidException
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        logger.error("MethodArgumentNotValidException caught: ", ex);

        final ResponseCode responseCode = ErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(ex, responseCode);
    }

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException e) {
        logger.error("ConstraintViolationException caught: ", e);

        final ResponseCode responseCode = ErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(e, responseCode);
    }

    /**
     * MethodArgumentTypeMismatchException
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
        logger.error("MethodArgumentTypeMismatchException caught: ", e);

        final ResponseCode responseCode = ErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(responseCode, responseCode.getMessage());
    }

    /**
     *   handleExceptionInternal
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(final Exception e) {
        logger.error("Exception caught: ", e);

        final ResponseCode responseCode = ErrorCode.INTERNAL_SERVER_ERROR;

        return handleExceptionInternal(responseCode);
    }

    @ExceptionHandler(AlreadyCreatedPersonalChatRoomException.class)
    public ResponseEntity<String> handleAlreadyCreatedPersonalChatRoomException(AlreadyCreatedPersonalChatRoomException ex) {
        // 예외 메시지와 함께 chatRoomId를 포함한 JSON 응답을 반환
        String errorMessage = "Personal chat room already exists.";
        Long chatRoomId = ex.getAlreadyCreatedChatRoomId(); // chatRoomId 값
        String jsonResponse = "{\"error\": \"" + errorMessage + "\", \"chatRoomId\": " + chatRoomId + "}";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
    }

    private ResponseEntity<Object> handleExceptionInternal(final ResponseCode responseCode) {
        return ResponseEntity.status(responseCode.getHttpStatus())
                .body(ErrorResponse.of(responseCode));
    }

    private ResponseEntity<Object> handleExceptionInternal(final ResponseCode responseCode, final String message) {
        return ResponseEntity.status(responseCode.getHttpStatus())
                .body(ErrorResponse.of(responseCode, message));
    }

    private ResponseEntity<Object> handleExceptionInternal(final BindException e, final ResponseCode responseCode) {
        return ResponseEntity.status(responseCode.getHttpStatus())
                .body(ErrorResponse.of(e, responseCode));
    }

    private ResponseEntity<Object> handleExceptionInternal(final ConstraintViolationException e, final ResponseCode responseCode) {
        return ResponseEntity.status(responseCode.getHttpStatus())
                .body(ErrorResponse.of(e, responseCode));
    }

}
