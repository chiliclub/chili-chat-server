package com.chiliclub.chilichat.controller;

import com.chiliclub.chilichat.entity.TestEntity;
import com.chiliclub.chilichat.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().body("test");
    }

    @GetMapping("/query")
    public ResponseEntity<TestEntity> query() {
        return ResponseEntity.ok(testService.testQuery());
    }
}
