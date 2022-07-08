package com.chiliclub.chilichat.service;

import com.chiliclub.chilichat.entity.TestEntity;
import com.chiliclub.chilichat.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    public TestEntity testQuery() {
        return testRepository.findById(1L).get();
    }
}
