package com.chiliclub.chilichat.common.exception;

import com.chiliclub.chilichat.common.enumeration.ErrorCode;

public class UserNotAuthorizedException extends BaseException {

    public UserNotAuthorizedException() {
        super(ErrorCode.USER_NOT_AUTHORIZED, "인증에 실패했습니다");
    }

    public UserNotAuthorizedException(String message) {
        super(ErrorCode.USER_NOT_AUTHORIZED, message);
    }

    public UserNotAuthorizedException(Throwable cause) {
        super(ErrorCode.USER_NOT_AUTHORIZED, "인증에 실패했습니다", cause);
    }

    public UserNotAuthorizedException(String message, Throwable cause) {
        super(ErrorCode.USER_NOT_AUTHORIZED, message, cause);
    }
}
