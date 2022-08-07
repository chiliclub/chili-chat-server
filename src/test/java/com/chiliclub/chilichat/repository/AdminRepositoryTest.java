package com.chiliclub.chilichat.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

//    @Test
//    void test() {
//        adminRepository.findByUserNo(1L);
//    }
}