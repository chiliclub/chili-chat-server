package com.chiliclub.chilichat.service;

import com.chiliclub.chilichat.common.exception.InvalidReqParamException;
import com.chiliclub.chilichat.config.TokenProvider;
import com.chiliclub.chilichat.entity.UserEntity;
import com.chiliclub.chilichat.model.user.UserDetailsImpl;
import com.chiliclub.chilichat.model.user.UserSaveRequest;
import com.chiliclub.chilichat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public Long saveUser(UserSaveRequest req) {

        validateDuplicatedUser(req); // 중복된 아이디와 닉네임 검사
        UserEntity userEntity = UserEntity.create(req, passwordEncoder);

        return userRepository.save(userEntity).getNo();
    }

    private void validateDuplicatedUser(UserSaveRequest req) {

        UserEntity foundUserById = userRepository.findByLoginId(req.getId());
        if (foundUserById != null) {
            throw new InvalidReqParamException("중복된 아이디입니다.");
        }

        UserEntity foundUserByNickname = userRepository.findByNickname(req.getNickname());
        if (foundUserByNickname != null) {
            throw new InvalidReqParamException("중복된 닉네임입니다.");
        }
    }

    public String signIn(String id, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(id, password)
        );

        return createJwtToken(authentication);
    }

    private String createJwtToken(Authentication authentication) {

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        return tokenProvider.generateToken(principal);
    }
}
