package com.chiliclub.chilichat.common.exception.handler;

import com.chiliclub.chilichat.common.enumeration.ErrorCode;
import com.chiliclub.chilichat.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.chiliclub.chilichat.common.utils.StringUtils.handleMessage;

@Slf4j
@RestControllerAdvice(basePackages = "com.chiliclub.chilichat.controller")
public class ServerExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException", e);
        return new ErrorResponse(
                ErrorCode.UNKNOWN_SERVER_ERROR.getName(),
                handleMessage(e.getMessage(), ErrorCode.UNKNOWN_SERVER_ERROR)
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        log.error("Exception", e);
        return new ErrorResponse(
                ErrorCode.INTERNAL_SERVER_ERROR.getName(),
                handleMessage(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR)
        );
    }
}
