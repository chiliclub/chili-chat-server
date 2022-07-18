package com.chiliclub.chilichat.common.exception;

import com.chiliclub.chilichat.common.enumeration.ErrorCode;

public class InvalidReqParamException extends BaseException {

    public InvalidReqParamException() {
        super(ErrorCode.INVALID_REQUEST_PARAMETER, "잘못된 요청 파라미터입니다.");
    }

    public InvalidReqParamException(String message) {
        super(ErrorCode.INVALID_REQUEST_PARAMETER, message);
    }

    public InvalidReqParamException(Throwable cause) {
        super(ErrorCode.INVALID_REQUEST_PARAMETER, "잘못된 요청 파라미터입니다.", cause);
    }

    public InvalidReqParamException(String message, Throwable cause) {
        super(ErrorCode.INVALID_REQUEST_PARAMETER, message, cause);
    }
}
