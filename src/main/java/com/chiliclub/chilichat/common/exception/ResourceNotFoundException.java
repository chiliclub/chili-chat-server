package com.chiliclub.chilichat.common.exception;

import com.chiliclub.chilichat.common.enumeration.ErrorCode;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException() {
        super(ErrorCode.RESOURCE_NOT_FOUND, "요청하신 데이터가 존재하지 않습니다.");
    }

    public ResourceNotFoundException(String message) {
        super(ErrorCode.RESOURCE_NOT_FOUND, message);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(ErrorCode.RESOURCE_NOT_FOUND, "요청하신 데이터가 존재하지 않습니다.", cause);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(ErrorCode.RESOURCE_NOT_FOUND, message, cause);
    }
}
