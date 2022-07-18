package com.chiliclub.chilichat.common.enumeration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Consts {

    public static String[] WHITE_LIST = {
            "/test/error/**", "/user/signup", "/user/signin"
    };
}
