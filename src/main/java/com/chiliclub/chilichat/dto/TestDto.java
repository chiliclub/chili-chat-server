package com.chiliclub.chilichat.dto;

import com.chiliclub.chilichat.entity.TestEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TestDto {
    @ApiModelProperty(value = "테스트ID")
    private Long id;

    @ApiModelProperty(value = "메시지")
    private String content;

    public static TestDto from(TestEntity testEntity) {
        return TestDto
                .builder()
                .id(testEntity.getId())
                .content(testEntity.getContent())
                .build();
    }
}
