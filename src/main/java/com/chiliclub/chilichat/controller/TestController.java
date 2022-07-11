package com.chiliclub.chilichat.controller;

import com.chiliclub.chilichat.common.exception.ResourceNotFoundException;
import com.chiliclub.chilichat.dto.TestDto;
import com.chiliclub.chilichat.entity.TestEntity;
import com.chiliclub.chilichat.service.TestService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().body("test");
    }

    @GetMapping("/query")
    public ResponseEntity<TestDto> query() {
        TestEntity entity = testService.testQuery();
        TestDto dto = modelMapper.map(entity, TestDto.class);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/error/server")
    public ResponseEntity<TestDto> serverError() {
        TestEntity entity = testService.testQuery();
        TestDto dto = modelMapper.map(entity, TestDto.class);
        throw new RuntimeException();
    }
    @GetMapping("/error/client")
    public ResponseEntity<TestDto> clientError() {
        TestEntity entity = testService.testQuery();
        TestDto dto = modelMapper.map(entity, TestDto.class);
        throw new ResourceNotFoundException();
    }
}
