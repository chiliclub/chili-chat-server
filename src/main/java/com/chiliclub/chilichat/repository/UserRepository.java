package com.chiliclub.chilichat.repository;

import com.chiliclub.chilichat.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByLoginId(String loginId);

    Optional<UserEntity> findByNickname(String nickname);
}
