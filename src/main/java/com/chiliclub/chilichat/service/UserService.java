package com.chiliclub.chilichat.service;

import com.chiliclub.chilichat.common.exception.InvalidReqParamException;
import com.chiliclub.chilichat.entity.UserEntity;
import com.chiliclub.chilichat.model.UserRegisterRequest;
import com.chiliclub.chilichat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long saveUser(UserRegisterRequest req) {

        validateDuplicatedUser(req); // 중복된 아이디와 닉네임 검사
        UserEntity userEntity = UserEntity.create(req, passwordEncoder);

        return userRepository.save(userEntity).getNo();
    }

    private void validateDuplicatedUser(UserRegisterRequest req) {
        UserEntity foundUserById = userRepository.findByLoginId(req.getId());
        if (foundUserById != null) {
            throw new InvalidReqParamException("중복된 아이디입니다.");
        }
        UserEntity foundUserByNickname = userRepository.findByNickname(req.getNickname());
        if (foundUserByNickname != null) {
            throw new InvalidReqParamException("중복된 닉네임입니다.");
        }
    }
}
