package com.chiliclub.chilichat.common.exception.handler;

import com.chiliclub.chilichat.common.enumeration.ErrorCode;
import com.chiliclub.chilichat.common.exception.InvalidReqParamException;
import com.chiliclub.chilichat.common.exception.MethodNotAllowedException;
import com.chiliclub.chilichat.common.exception.ResourceNotFoundException;
import com.chiliclub.chilichat.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice(basePackages = "com.chiliclub.chilichat.controller")
public class ClientExceptionHandler {

    private String handleMessage(String message, ErrorCode errorCode) {
        StringBuilder stringBuilder = new StringBuilder("Client Error: ");
        if (message == null) stringBuilder.append(errorCode.getName());
        else stringBuilder.append(message);
        return stringBuilder.toString();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
        log.error("ConstraintViolationException", e);
        return new ErrorResponse(
                ErrorCode.INVALID_REQUEST_PARAMETER.getName(),
                handleMessage(e.getMessage(), ErrorCode.INVALID_REQUEST_PARAMETER)
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException", e);
        return new ErrorResponse(
                ErrorCode.INVALID_REQUEST_PARAMETER.getName(),
                handleMessage(e.getMessage(), ErrorCode.INVALID_REQUEST_PARAMETER)
        );
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBindException(BindException e) {
        log.error("BindException", e);
        return new ErrorResponse(
                ErrorCode.INVALID_REQUEST_PARAMETER.getName(),
                handleMessage(e.getMessage(), ErrorCode.INVALID_REQUEST_PARAMETER)
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("MethodArgumentTypeMismatchException", e);
        return new ErrorResponse(
                ErrorCode.INVALID_REQUEST_PARAMETER.getName(),
                handleMessage(e.getMessage(), ErrorCode.INVALID_REQUEST_PARAMETER)
        );
    }

    @ExceptionHandler(InvalidReqParamException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidReqParamException(InvalidReqParamException e) {
        log.error("InvalidReqParamException", e);
        return new ErrorResponse(
                ErrorCode.INVALID_REQUEST_PARAMETER.getName(),
                handleMessage(e.getMessage(), ErrorCode.INVALID_REQUEST_PARAMETER)
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException e) {
        log.error("AccessDeniedException", e);
        return new ErrorResponse(
                ErrorCode.METHOD_NOT_ALLOWED.getName(),
                handleMessage(e.getMessage(), ErrorCode.METHOD_NOT_ALLOWED)
        );
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleMethodNotAllowedException(MethodNotAllowedException e) {
        log.error("MethodNotAllowedException", e);
        return new ErrorResponse(
                e.getErrorCode().getName(),
                handleMessage(e.getMessage(), e.getErrorCode())
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error("ResourceNotFoundException", e);
        return new ErrorResponse(
                e.getErrorCode().getName(),
                handleMessage(e.getMessage(), e.getErrorCode())
        );
    }
}
