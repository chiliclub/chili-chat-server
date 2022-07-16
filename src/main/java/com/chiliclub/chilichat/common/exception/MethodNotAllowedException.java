package com.chiliclub.chilichat.common.exception;

import com.chiliclub.chilichat.common.enumeration.ErrorCode;

public class MethodNotAllowedException extends BaseException {

    public MethodNotAllowedException() {
        super(ErrorCode.METHOD_NOT_ALLOWED, "권한이 없습니다.");
    }

    public MethodNotAllowedException(String message) {
        super(ErrorCode.METHOD_NOT_ALLOWED, message);
    }

    public MethodNotAllowedException(Throwable cause) {
        super(ErrorCode.METHOD_NOT_ALLOWED, "권한이 없습니다.", cause);
    }

    public MethodNotAllowedException(String message, Throwable cause) {
        super(ErrorCode.METHOD_NOT_ALLOWED, message, cause);
    }
}
