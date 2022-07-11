package com.chiliclub.chilichat.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // 400
    INVALID_REQUEST_PARAMETER("Invalid Request Parameter"),
    // 401
    METHOD_NOT_ALLOWED("Method Not Allowed"),
    // 404
    RESOURCE_NOT_FOUND("Resource Not Found"),

    // 500
    UNKNOWN_SERVER_ERROR("Runtime Server Error"),
    INTERNAL_SERVER_ERROR("Internal Server Error")
    ;

    private final String name;
}
