package com.chiliclub.chilichat.model;

import com.chiliclub.chilichat.model.user.UserSaveRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserSaveRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("모든 검증에 성공한다")
    void tesSuccessToValidateId() {

        // given
        UserSaveRequest req = UserSaveRequest.builder()
                .id("tester1")
                .password("test1234")
                .nickname("무키무키")
                .build();

        // when
        Set<ConstraintViolation<UserSaveRequest>> violations = validator.validate(req);

        // then
        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("아이디에 영문자가 포함되지 않으면 검증에 실패한다")
    void testFailToValidateIdIfIsAlphabetIsNotIncluded() {

        // given
        UserSaveRequest req = UserSaveRequest.builder()
                .id("111111")
                .password("test1234")
                .nickname("무키무키")
                .build();

        // when
        Set<ConstraintViolation<UserSaveRequest>> violations = validator.validate(req);

        // then
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("아이디는 영문자를 포함한 5-30자리 이내");
    }

    @Test
    @DisplayName("아이디에 특수문자가 포함되면 검증에 실패한다")
    void testFailToValidateIdIfSpecialCharacterIsIncluded() {

        // given
        UserSaveRequest req = UserSaveRequest.builder()
                .id("tester1!")
                .password("test1234")
                .nickname("무키무키")
                .build();

        // when
        Set<ConstraintViolation<UserSaveRequest>> violations = validator.validate(req);

        // then
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("아이디는 영문자를 포함한 5-30자리 이내");
    }

    @Test
    @DisplayName("아이디에 공백이 포함되면 검증에 실패한다")
    void testFailToValidateIdIfBlankIsIncluded() {

        // given
        UserSaveRequest req = UserSaveRequest.builder()
                .id("tesadsfadf ter1")
                .password("test1234")
                .nickname("무키무키")
                .build();

        // when
        Set<ConstraintViolation<UserSaveRequest>> violations = validator.validate(req);

        // then
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("아이디는 영문자를 포함한 5-30자리 이내");
    }

    @Test
    @DisplayName("아이디가 5자 미만이면 검증에 실패한다")
    void testFailToValidateIdIfLengthIsLessThan5() {

        // given
        UserSaveRequest req = UserSaveRequest.builder()
                .id("test!")
                .password("test1234")
                .nickname("무키무키")
                .build();

        // when
        Set<ConstraintViolation<UserSaveRequest>> violations = validator.validate(req);

        // then
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("아이디는 영문자를 포함한 5-30자리 이내");
    }

    @Test
    @DisplayName("아이디가 30자 초과면 검증에 실패한다")
    void testFailToValidateIdIfLengthIsMoreThan30() {

        // given
        UserSaveRequest req = UserSaveRequest.builder()
                .id("TestTestTestTestTestTestTestTest")
                .password("test1234")
                .nickname("무키무키")
                .build();

        // when
        Set<ConstraintViolation<UserSaveRequest>> violations = validator.validate(req);

        // then
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("아이디는 영문자를 포함한 5-30자리 이내");
    }

    @Test
    @DisplayName("비밀번호에 영문자가 포함되지 않으면 검증에 실패한다")
    void testFailToValidatePasswordIfAlphabetIsNotIncluded() {

        // given
        UserSaveRequest req = UserSaveRequest.builder()
                .id("tester1")
                .password("12341234")
                .nickname("무키무키")
                .build();

        // when
        Set<ConstraintViolation<UserSaveRequest>> violations = validator.validate(req);

        // then
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("비밀번호는 영문자와 숫자를 포함한 8-30자리 이내");
    }

    @Test
    @DisplayName("비밀번호에 숫자가 포함되지 않으면 검증에 실패한다")
    void testFailToValidatePasswordIfNumberIsNotIncluded() {

        // given
        UserSaveRequest req = UserSaveRequest.builder()
                .id("tester1")
                .password("testtest")
                .nickname("무키무키")
                .build();

        // when
        Set<ConstraintViolation<UserSaveRequest>> violations = validator.validate(req);

        // then
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("비밀번호는 영문자와 숫자를 포함한 8-30자리 이내");
    }

    @Test
    @DisplayName("비밀번호에 특수문자가 포함되면 검증에 실패한다")
    void testFailToValidatePasswordIfSpecialCharacterIsIncluded() {

        // given
        UserSaveRequest req = UserSaveRequest.builder()
                .id("tester1")
                .password("test1234!")
                .nickname("무키무키")
                .build();

        // when
        Set<ConstraintViolation<UserSaveRequest>> violations = validator.validate(req);

        // then
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("비밀번호는 영문자와 숫자를 포함한 8-30자리 이내");
    }

    @Test
    @DisplayName("비밀번호에 공백이 포함되면 검증에 실패한다")
    void testFailToValidatePasswordIfBlankIsIncluded() {

        // given
        UserSaveRequest req = UserSaveRequest.builder()
                .id("tester1")
                .password("test1234 test1234")
                .nickname("무키무키")
                .build();

        // when
        Set<ConstraintViolation<UserSaveRequest>> violations = validator.validate(req);

        // then
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("비밀번호는 영문자와 숫자를 포함한 8-30자리 이내");
    }

    @Test
    @DisplayName("비밀번호가 8자 미만이면 검증에 실패한다")
    void testFailToValidatePasswordIfLengthIsLessThan8() {

        // given
        UserSaveRequest req = UserSaveRequest.builder()
                .id("tester1")
                .password("test123")
                .nickname("무키무키")
                .build();

        // when
        Set<ConstraintViolation<UserSaveRequest>> violations = validator.validate(req);

        // then
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("비밀번호는 영문자와 숫자를 포함한 8-30자리 이내");
    }

    @Test
    @DisplayName("비밀번호가 30자 초과면 검증에 실패한다")
    void testFailToValidatePasswordIfLengthIsMoreThan30() {

        // given
        UserSaveRequest req = UserSaveRequest.builder()
                .id("tester1")
                .password("TestTestTestTestTestTestTestTest")
                .nickname("무키무키")
                .build();

        // when
        Set<ConstraintViolation<UserSaveRequest>> violations = validator.validate(req);

        // then
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("비밀번호는 영문자와 숫자를 포함한 8-30자리 이내");
    }

    @Test
    @DisplayName("닉네임이 공백문자로만 이루어져있다면 검증에 실패한다")
    void testFailToValidateNicknameIfOnlyComposedOfBlank() {

        // given
        UserSaveRequest req = UserSaveRequest.builder()
                .id("tester1")
                .password("test1234")
                .nickname("   ")
                .build();

        // when
        Set<ConstraintViolation<UserSaveRequest>> violations = validator.validate(req);

        // then
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("닉네임은 공백문자로만 이루어질 수 없습니다");
    }

    @Test
    @DisplayName("닉네임이 2자 미만이면 검증에 실패한다")
    void testFailToValidateNicknameIfLengthIsLessThan2() {

        // given
        UserSaveRequest req = UserSaveRequest.builder()
                .id("tester1")
                .password("test1234")
                .nickname("욱")
                .build();

        // when
        Set<ConstraintViolation<UserSaveRequest>> violations = validator.validate(req);

        // then
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("닉네임은 2-10자리 이내");
    }

    @Test
    @DisplayName("닉네임이 10자 초과면 검증에 실패한다")
    void testFailToValidateNicknameIfLengthIsMoreThan10() {

        // given
        UserSaveRequest req = UserSaveRequest.builder()
                .id("tester1")
                .password("test1234")
                .nickname("배고파요밥주세요엉엉엉엉")
                .build();

        // when
        Set<ConstraintViolation<UserSaveRequest>> violations = validator.validate(req);

        // then
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("닉네임은 2-10자리 이내");
    }
}