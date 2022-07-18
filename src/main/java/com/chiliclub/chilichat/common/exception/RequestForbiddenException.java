package com.chiliclub.chilichat.common.exception;

import com.chiliclub.chilichat.common.enumeration.ErrorCode;

public class RequestForbiddenException extends BaseException {

    public RequestForbiddenException() {
        super(ErrorCode.REQUEST_FORBIDDEN, "권한이 없습니다.");
    }

    public RequestForbiddenException(String message) {
        super(ErrorCode.REQUEST_FORBIDDEN, message);
    }

    public RequestForbiddenException(Throwable cause) {
        super(ErrorCode.REQUEST_FORBIDDEN, "권한이 없습니다.", cause);
    }

    public RequestForbiddenException(String message, Throwable cause) {
        super(ErrorCode.REQUEST_FORBIDDEN, message, cause);
    }
}
