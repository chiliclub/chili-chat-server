package com.chiliclub.chilichat.model;

import com.chiliclub.chilichat.entity.TestEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @ApiModelProperty(value = "생성시간")
    private LocalDateTime insDatetime;

    @ApiModelProperty(value = "수정시간")
    private LocalDateTime updDatetime;

    public static TestDto from(TestEntity testEntity) {
        return TestDto
                .builder()
                .id(testEntity.getId())
                .content(testEntity.getContent())
                .build();
    }
}
