package com.chiliclub.chilichat.model.user;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserModifyRequest {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel
    public static class Nickname {
        private Long userNo;
        private String nickname;
    }
}
