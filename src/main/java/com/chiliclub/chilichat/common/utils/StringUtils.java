package com.chiliclub.chilichat.common.utils;

import com.chiliclub.chilichat.common.enumeration.ErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtils {

    public static String handleMessage(String message, ErrorCode errorCode) {
        if (message == null) return errorCode.getName();
        else return message;
    }
}
