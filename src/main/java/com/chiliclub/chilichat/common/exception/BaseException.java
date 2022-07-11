package com.chiliclub.chilichat.common.exception;


import com.chiliclub.chilichat.common.enumeration.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BaseException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;
}
