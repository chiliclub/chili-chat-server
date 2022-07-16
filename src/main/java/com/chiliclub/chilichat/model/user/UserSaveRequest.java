package com.chiliclub.chilichat.model.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@ApiModel
public class UserSaveRequest {

    @ApiModelProperty(
            required = true,
            value = "아이디",
            example = "tester1"
    )
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,30}",
            message = "아이디는 영문자를 포함한 5-30자리 이내"
    )
    private String id;

    @ApiModelProperty(
            required = true,
            value = "비밀번호",
            example = "test1234"
    )
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,30}",
            message = "비밀번호는 영문자와 숫자를 포함한 8-30자리 이내"
    )
    private String password;

    @ApiModelProperty(
            required = true,
            value = "닉네임",
            example = "무키무키"
    )
    @NotBlank(message = "닉네임은 공백문자로만 이루어질 수 없습니다")
    @Size(min = 2, max = 10, message = "닉네임은 2-10자리 이내")
    private String nickname;

    @Builder
    public UserSaveRequest(String id, String password, String nickname) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
    }
}
