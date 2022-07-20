package com.chiliclub.chilichat.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@ApiModel
public class ChatRoomUpdateRequest {
    @ApiModelProperty(
            required = true,
            value = "수정할 채팅방 이름",
            example = "파이썬 1:1 초보만!"
    )
    @Pattern(
            regexp = "[^?a-zA-Z\\d/]{1,30}",
            message = "채팅방 이름은 1~30자 이내"
    )
    private String title;
}
