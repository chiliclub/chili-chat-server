package com.chiliclub.chilichat.service;

import com.chiliclub.chilichat.common.exception.InvalidReqParamException;
import com.chiliclub.chilichat.common.exception.RequestForbiddenException;
import com.chiliclub.chilichat.common.exception.ResourceNotFoundException;
import com.chiliclub.chilichat.common.exception.UserNotAuthorizedException;
import com.chiliclub.chilichat.component.S3Uploader;
import com.chiliclub.chilichat.component.TokenProvider;
import com.chiliclub.chilichat.entity.UserChatRoomEntity;
import com.chiliclub.chilichat.entity.UserEntity;
import com.chiliclub.chilichat.model.user.UserDetailsImpl;
import com.chiliclub.chilichat.model.user.UserInfoResponse;
import com.chiliclub.chilichat.model.user.UserSaveRequest;
import com.chiliclub.chilichat.model.user.UserSignInResponse;
import com.chiliclub.chilichat.repository.UserChatRoomRepository;
import com.chiliclub.chilichat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final S3Uploader s3Uploader;

    @Transactional
    public Long saveUser(UserSaveRequest req) {

        validateDuplicatedUser(req); // 중복된 아이디와 닉네임 검사
        UserEntity userEntity = UserEntity.createFrom(
                req,
                passwordEncoder,
                s3Uploader.getDefaultPicUrl());

        return userRepository.save(userEntity).getNo();
    }

    private void validateDuplicatedUser(UserSaveRequest req) {

        if (isLoginIdDuplicated(req.getId())) {
            throw new InvalidReqParamException("중복된 아이디입니다.");
        }

        if (isNicknameDuplicated(req.getNickname())) {
            throw new InvalidReqParamException("중복된 닉네임입니다.");
        }
    }

    private boolean isNicknameDuplicated(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    private boolean isLoginIdDuplicated(String loginId) {
        return userRepository.findByLoginId(loginId).isPresent();
    }

    public UserSignInResponse signIn(String id, String password) {

        final Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(id, password)
            );
        } catch (BadCredentialsException e) {
            throw new UserNotAuthorizedException();
        }

        String token = createJwtToken(authentication);

        return UserSignInResponse.from(token);
    }

    private String createJwtToken(Authentication authentication) {

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        return tokenProvider.generateToken(principal);
    }

    public Long getCurrentUserNo() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        UserEntity userEntity = userRepository.findById(principal.getUserNo())
                .orElseThrow(() -> new RequestForbiddenException("현재 인증된 유저가 존재하지 않습니다."));

        return userEntity.getNo();
    }

    public UserInfoResponse getUserInfo(Long userNo) {

        UserEntity userEntity = userRepository.findById(userNo)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 유저입니다."));

        return UserInfoResponse.from(userEntity);
    }

    private void checkUserAuth(Long userNo) {
        if (userNo.compareTo(getCurrentUserNo()) != 0) {
            throw new RequestForbiddenException();
        }
    }

    @Transactional
    public String setUserNickname(Long userNo, String nickname) {

        checkUserAuth(userNo);

        String newNickname = validateNickname(nickname);

        UserEntity userEntity = userRepository.findById(userNo)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 유저입니다."));

        userEntity.updateNickname(newNickname);

        return newNickname;
    }

    private String validateNickname(String nickname) {
        if (nickname == null) {
            throw new InvalidReqParamException("닉네임은 null이 될 수 없습니다.");
        }
        if (nickname.isBlank()) {
            throw new InvalidReqParamException("닉네임은 공백문자로만 이루어질 수 없습니다.");
        }
        if (nickname.length() < 2 || nickname.length() > 10) {
            throw new InvalidReqParamException("닉네임은 2-10자리 이내입니다.");
        }
        if (isNicknameDuplicated(nickname)) {
            throw new InvalidReqParamException("중복된 닉네임입니다.");
        }
        return nickname.trim();
    }

    @Transactional
    public String setUserPicUrl(Long userNo, String picUrl) {

        checkUserAuth(userNo);
        validatePicUrl(picUrl);

        UserEntity userEntity = userRepository.findById(userNo)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 유저입니다."));

        userEntity.updatePicUrl(picUrl);

        return picUrl;
    }

    private void validatePicUrl(String picUrl) {
        if (picUrl.isBlank()) {
            throw new InvalidReqParamException("닉네임은 공백문자로만 이루어질 수 없습니다.");
        }
    }

    public List<UserInfoResponse> getUserInfosByChatRoomNo(Long chatRoomNo) {

        List<UserChatRoomEntity> userChatRoomEntities = userChatRoomRepository.findByNo(chatRoomNo);

        return userChatRoomEntities.stream()
                .map(e -> UserInfoResponse.from(e.getUser()))
                .collect(Collectors.toList());
    }
}
