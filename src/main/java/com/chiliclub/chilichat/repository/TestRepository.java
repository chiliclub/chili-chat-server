package com.chiliclub.chilichat.repository;

import com.chiliclub.chilichat.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long> {
}
