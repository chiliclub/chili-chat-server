package com.chiliclub.chilichat.service;

import com.chiliclub.chilichat.entity.UserEntity;
import com.chiliclub.chilichat.model.user.UserDetailsImpl;
import com.chiliclub.chilichat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByLoginId(loginId).orElseThrow(
                () -> new UsernameNotFoundException(loginId)
        );

        return UserDetailsImpl.builder()
                .username(userEntity.getLoginId())
                .password(userEntity.getPassword())
                .nickname(userEntity.getNickname())
                .build();
    }
}
