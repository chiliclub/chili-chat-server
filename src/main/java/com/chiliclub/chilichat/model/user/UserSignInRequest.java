package com.chiliclub.chilichat.model.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel
public class UserSignInRequest {

    @ApiModelProperty(
            required = true,
            value = "아이디",
            example = "tester1"
    )
    private String id;

    @ApiModelProperty(

            required = true,
            value = "비밀번호",
            example = "test1234"
    )
    private String password;
}
