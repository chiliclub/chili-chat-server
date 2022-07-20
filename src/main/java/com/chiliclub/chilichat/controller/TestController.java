package com.chiliclub.chilichat.controller;

import com.chiliclub.chilichat.common.exception.ResourceNotFoundException;
import com.chiliclub.chilichat.component.S3Uploader;
import com.chiliclub.chilichat.entity.TestEntity;
import com.chiliclub.chilichat.model.TestDto;
import com.chiliclub.chilichat.service.TestService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final ModelMapper modelMapper;
    private final TestService testService;
    private final S3Uploader s3Uploader;

    @GetMapping
    public ResponseEntity<String> test() {
        return ok().body("test");
    }

    @GetMapping("/query")
    public ResponseEntity<TestDto> query() {
        TestEntity entity = testService.testQuery();
        TestDto dto = modelMapper.map(entity, TestDto.class);
        return ok().body(dto);
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

    @PostMapping("/s3")
    public ResponseEntity<String> upload(@RequestPart MultipartFile file) throws IOException {
        return ResponseEntity.ok(s3Uploader.upload(file));
    }

}
