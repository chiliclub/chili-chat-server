package com.chiliclub.chilichat.controller;

import com.chiliclub.chilichat.model.UserRegisterRequest;
import com.chiliclub.chilichat.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원 등록")
    @PostMapping("/signup")
    public ResponseEntity<Long> userRegister(@RequestBody UserRegisterRequest req) {

        Long userNo = userService.saveUser(req);
        return ResponseEntity.ok(userNo);
    }
}
