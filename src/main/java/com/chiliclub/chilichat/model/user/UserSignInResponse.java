package com.chiliclub.chilichat.model.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel
public class UserSignInResponse {

    @ApiModelProperty(
            value = "액세스 토큰"
    )
    private String token;

    @Builder
    public UserSignInResponse(String token) {
        this.token = token;
    }
}
