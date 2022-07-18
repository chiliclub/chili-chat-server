package com.chiliclub.chilichat.common.utils;

import com.chiliclub.chilichat.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {

    @Test
    @DisplayName("메세지가 없으면 기본 예외 메시지를 반환한다")
    void testSuccessToHandleMessageIfMessageIsNull() {

        // given
        ResourceNotFoundException e = new ResourceNotFoundException();

        // when
        String errorMessage = StringUtils.handleMessage(e.getMessage(), e.getErrorCode());

        // then
        assertThat(errorMessage).isEqualTo("요청하신 데이터가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("메세지가 있으면 해당 메시지를 반환한다")
    void testSuccessToHandleMessageIfMessageIsNotNull() {

        // given
        ResourceNotFoundException e = new ResourceNotFoundException("404 에러입니다.");

        // when
        String errorMessage = StringUtils.handleMessage(e.getMessage(), e.getErrorCode());

        // then
        assertThat(errorMessage).isEqualTo("404 에러입니다.");
    }

}