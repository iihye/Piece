package com.ssafy.user.global.response.code;

import org.springframework.http.HttpStatus;

public interface ResponseCode {

    String name();

    HttpStatus getHttpStatus();

    String getMessage();

}
