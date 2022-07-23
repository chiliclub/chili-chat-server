package com.chiliclub.chilichat.controller;

import com.chiliclub.chilichat.model.user.UserInfoResponse;
import com.chiliclub.chilichat.model.user.UserSaveRequest;
import com.chiliclub.chilichat.model.user.UserSignInRequest;
import com.chiliclub.chilichat.model.user.UserSignInResponse;
import com.chiliclub.chilichat.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원 등록")
    @PostMapping("/signup")
    public ResponseEntity<Long> userSave(@RequestBody UserSaveRequest req) {
        return ResponseEntity.ok(userService.saveUser(req));
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/signin")
    public ResponseEntity<UserSignInResponse> userSignIn(@RequestBody UserSignInRequest req, HttpServletResponse res) {
        return ResponseEntity.ok(userService.signIn(req.getId(), req.getPassword()));
    }

    @ApiOperation(value = "유저 프로필 조회")
    @GetMapping("/{userNo}")
    public ResponseEntity<UserInfoResponse> userInfo(
            @ApiParam(value = "ex) 2") @PathVariable Long userNo
    ) {
        return ResponseEntity.ok(userService.getUserInfo(userNo));
    }

    @ApiOperation(
            value = "본인의 프로필 조회"
    )
    @GetMapping("/my")
    public ResponseEntity<UserInfoResponse> userMy() {
        return ResponseEntity.ok(userService.getUserInfo(
                userService.getCurrentUserNo()
        ));
    }
}
