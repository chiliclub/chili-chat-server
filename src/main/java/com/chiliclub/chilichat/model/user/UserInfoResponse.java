package com.chiliclub.chilichat.model.user;

import com.chiliclub.chilichat.entity.UserEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel
public class UserInfoResponse {

    @ApiModelProperty(
            value = "유저ID"
    )
    private Long userNo;

    @ApiModelProperty(
            value = "아이디"
    )
    private String loginId;

    @ApiModelProperty(
            value = "닉네임"
    )
    private String nickname;

    @ApiModelProperty(
            value = "프로필 이미지 URL"
    )
    private String picUrl;

    @Builder
    public UserInfoResponse(
            Long userNo,
            String loginId,
            String nickname,
            String picUrl) {

        this.userNo = userNo;
        this.loginId = loginId;
        this.nickname = nickname;
        this.picUrl = picUrl;
    }

    public static UserInfoResponse from(UserEntity userEntity) {
        return UserInfoResponse.builder()
                .userNo(userEntity.getNo())
                .loginId(userEntity.getLoginId())
                .nickname(userEntity.getNickname())
                .picUrl(userEntity.getPicUrl())
                .build();
    }
}
