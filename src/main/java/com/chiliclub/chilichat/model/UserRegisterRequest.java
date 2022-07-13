package com.chiliclub.chilichat.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel
public class UserRegisterRequest {

    @ApiModelProperty(
            required = true,
            value = "아이디",
            example = "tester1"
    )
    private String id;
    @ApiModelProperty(
            required = true,
            value = "비밀번호",
            example = "#test1234"
    )
    private String password;
    @ApiModelProperty(
            required = true,
            value = "닉네임",
            example = "무키무키"
    )
    private String nickname;

    @Builder
    public UserRegisterRequest(String id, String password, String nickname) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
    }
}
