package com.chiliclub.chilichat.service;

import com.chiliclub.chilichat.entity.UserEntity;
import com.chiliclub.chilichat.model.user.UserDetailsImpl;
import com.chiliclub.chilichat.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @DisplayName("아이디로 유저 정보를 가져오는 데 성공한다")
    void testSuccessToLoadUserByUsername() {

        // given
        String loginId = "tester1";
        UserEntity mockUserEntity = mock(UserEntity.class);

        given(mockUserEntity.getLoginId()).willReturn("tester1");
        given(mockUserEntity.getPassword()).willReturn("mockPassword");
        given(mockUserEntity.getNickname()).willReturn("무키무키");

        given(userRepository.findByLoginId(loginId)).willReturn(mockUserEntity);

        // when
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetailsService.loadUserByUsername(loginId);

        // then
        assertThat(userDetailsImpl.getUsername()).isEqualTo("tester1");
        assertThat(userDetailsImpl.getPassword()).isEqualTo("mockPassword");
        assertThat(userDetailsImpl.getNickname()).isEqualTo("무키무키");
    }

    @Test
    @DisplayName("아이디로 유저 정보를 가져오는 데 실패한다")
    void testFailToLoadUserByUsername() {

        // given
        String loginId = "tester1";

        given(userRepository.findByLoginId(loginId)).willReturn(null);

        // when && then
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername(loginId))
                .isInstanceOf(UsernameNotFoundException.class);
    }

}