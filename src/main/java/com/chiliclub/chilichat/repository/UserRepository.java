package com.chiliclub.chilichat.repository;

import com.chiliclub.chilichat.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
