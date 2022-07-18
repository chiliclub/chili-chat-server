package com.chiliclub.chilichat.model.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@ApiModel
public class UserSignInRequest {

    @ApiModelProperty(
            required = true,
            value = "아이디",
            example = "tester1"
    )
    @NotBlank
    private String id;

    @ApiModelProperty(

            required = true,
            value = "비밀번호",
            example = "test1234"
    )
    @NotBlank
    private String password;
}
