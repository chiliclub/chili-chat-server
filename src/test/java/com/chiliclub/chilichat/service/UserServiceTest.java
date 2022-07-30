package com.chiliclub.chilichat.service;

import com.chiliclub.chilichat.common.exception.InvalidReqParamException;
import com.chiliclub.chilichat.common.exception.RequestForbiddenException;
import com.chiliclub.chilichat.common.exception.ResourceNotFoundException;
import com.chiliclub.chilichat.component.S3Uploader;
import com.chiliclub.chilichat.entity.ChatRoomEntity;
import com.chiliclub.chilichat.entity.UserChatRoomEntity;
import com.chiliclub.chilichat.entity.UserEntity;
import com.chiliclub.chilichat.model.user.UserInfoResponse;
import com.chiliclub.chilichat.model.user.UserSaveRequest;
import com.chiliclub.chilichat.repository.UserChatRoomRepository;
import com.chiliclub.chilichat.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserChatRoomRepository userChatRoomRepository;
    @Spy
    private PasswordEncoder passwordEncoder;
    @Mock
    private S3Uploader s3Uploader;
    @Spy
    @InjectMocks
    private UserService userService;

    private UserSaveRequest createAddUserRequest() {
        return UserSaveRequest.builder()
                .id("tester1")
                .password("test1234")
                .nickname("무키무키")
                .build();
    }

    private UserEntity createUserEntityFrom(
            UserSaveRequest req,
            Long userNo,
            String defaultPicUrl
    ) {

        UserEntity userEntity = UserEntity.createFrom(
                req,
                passwordEncoder,
                defaultPicUrl);

        ReflectionTestUtils.setField(userEntity, "no", userNo);

        return userEntity;
    }

    private UserEntity createUserEntity(
            Long userNo,
            String loginId,
            String password,
            String nickname,
            PasswordEncoder passwordEncoder,
            String defaultPicUrl
    ) {

        UserEntity userEntity = UserEntity.create(
                loginId,
                password,
                nickname,
                passwordEncoder,
                defaultPicUrl);

        ReflectionTestUtils.setField(userEntity, "no", userNo);

        return userEntity;
    }


    @Test
    @DisplayName("정상적으로 유저를 저장한다")
    void testSuccessToSaveUser() {

        // given
        UserSaveRequest req = createAddUserRequest();
        Long userNo = 1L;
        String defaultPicUrl = "https://test/img.png";

        UserEntity userEntity = createUserEntityFrom(
                req,
                userNo,
                defaultPicUrl);

        given(userRepository.findByLoginId(any(String.class)))
                .willReturn(Optional.empty());
        given(userRepository.findByNickname(any(String.class)))
                .willReturn(Optional.empty());
        given(s3Uploader.getDefaultPicUrl())
                .willReturn(defaultPicUrl);
        given(userRepository.save(any(UserEntity.class)))
                .willReturn(userEntity);

        // when
        Long createdUserNo = userService.saveUser(req);

        // then
        assertThat(createdUserNo).isEqualTo(1L);
    }

    @Test
    @DisplayName("아이디 중복으로 유저를 저장하는 데 실패한다")
    void testFailToSaveUserIfIdIsDuplicated() {

        // given
        UserSaveRequest req = createAddUserRequest();
        Long userNo = 1L;
        String defaultPicUrl = "https://test/img.png";

        UserEntity userEntity = createUserEntityFrom(
                req,
                userNo,
                defaultPicUrl);

        given(userRepository.findByLoginId(any(String.class)))
                .willReturn(Optional.of(userEntity));

        // when && then
        assertThatThrownBy(() -> userService.saveUser(req))
                .isInstanceOf(InvalidReqParamException.class)
                .hasMessage("중복된 아이디입니다.");
    }

    @Test
    @DisplayName("닉네임 중복으로 유저를 저장하는 데 실패한다")
    void testFailToSaveUserIfNicknameIsDuplicated() {

        // given
        UserSaveRequest req = createAddUserRequest();
        Long userNo = 1L;
        String defaultPicUrl = "https://test/img.png";

        UserEntity userEntity = createUserEntityFrom(
                req,
                userNo,
                defaultPicUrl);

        given(userRepository.findByLoginId(any(String.class)))
                .willReturn(Optional.empty());
        given(userRepository.findByNickname(any(String.class)))
                .willReturn(Optional.of(userEntity));

        // when && then
        assertThatThrownBy(() -> userService.saveUser(req))
                .isInstanceOf(InvalidReqParamException.class)
                .hasMessage("중복된 닉네임입니다.");
    }

    @Test
    @DisplayName("유저 프로필을 가져오는 데 성공한다")
    void testSuccessToGetUserInfo() {

        // given
        Long userNo = 1L;

        UserEntity userEntity = createUserEntity(
                userNo,
                "tester1",
                "password123",
                "무키무키",
                passwordEncoder,
                "https://test/img.png"
        );

        UserInfoResponse userInfo = UserInfoResponse.from(userEntity);

        given(userRepository.findById(userNo))
                .willReturn(Optional.of(userEntity));

        // when
        UserInfoResponse result = userService.getUserInfo(userNo);

        // then
        assertThat(userInfo.getUserNo()).isEqualTo(result.getUserNo());
        assertThat(userInfo.getLoginId()).isEqualTo(result.getLoginId());
        assertThat(userInfo.getNickname()).isEqualTo(result.getNickname());
        assertThat(userInfo.getPicUrl()).isEqualTo(result.getPicUrl());
    }

    @Test
    @DisplayName("유저가 존재하지 않을 경우 유저 프로필을 가져오는 데 실패한다")
    void testFailToGetUserInfoIfUserIsNotExist() {

        // given
        Long userNo = 1L;

        given(userRepository.findById(userNo))
                .willReturn(Optional.empty());

        // when && then
        assertThatThrownBy(() -> userService.getUserInfo(userNo))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }

    @Test
    @DisplayName("유저의 닉네임을 수정하는 데 성공한다")
    void testSuccessToSetUserNickname() {

        // given
        Long userNo = 1L;

        UserEntity userEntity = createUserEntity(
                userNo,
                "tester1",
                "password123",
                "무키무키",
                passwordEncoder,
                "https://test/img.png"
        );

        String newNickname = "루드 굴리트";

        doReturn(userNo).when(userService).getCurrentUserNo();
        given(userRepository.findById(userNo)).willReturn(Optional.of(userEntity));

        // when
        String result = userService.setUserNickname(userNo, newNickname);

        // then
        assertThat(userEntity.getNickname()).isEqualTo(newNickname);
        assertThat(newNickname).isEqualTo(result);
    }

    @Test
    @DisplayName("인증되지 않을 경우 유저의 닉네임을 수정하는 데 실패한다")
    void testFailToSetUserNicknameIfUserIsNotAuthorized() {

        // given
        Long userNo = 1L;
        String newNickname = "루드 굴리트";

        doReturn(2L).when(userService).getCurrentUserNo();

        // when && then
        assertThatThrownBy(() -> userService.setUserNickname(userNo, newNickname))
                .isInstanceOf(RequestForbiddenException.class);
    }

    @Test
    @DisplayName("유저가 존재하지 않을 경우 유저의 닉네임을 수정하는 데 실패한다")
    void testFailToSetUserNicknameIfUserIsNotExist() {

        // given
        Long userNo = 1L;
        String newNickname = "루드 굴리트";

        doReturn(userNo).when(userService).getCurrentUserNo();
        given(userRepository.findById(userNo))
                .willReturn(Optional.empty());

        // when && then
        assertThatThrownBy(() -> userService.setUserNickname(userNo, newNickname))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }

    @Test
    @DisplayName("유저의 프로필 사진을 수정하는 데 성공한다")
    void testSuccessToSetUserPicUrl() {

        // given
        Long userNo = 1L;

        UserEntity userEntity = createUserEntity(
                userNo,
                "tester1",
                "password123",
                "무키무키",
                passwordEncoder,
                "https://test/img.png"
        );

        String newPicUrl = "https://test/new-img.png";

        doReturn(userNo).when(userService).getCurrentUserNo();
        given(userRepository.findById(userNo)).willReturn(Optional.of(userEntity));

        // when
        String result = userService.setUserPicUrl(userNo, newPicUrl);

        // then
        assertThat(userEntity.getPicUrl()).isEqualTo(newPicUrl);
        assertThat(newPicUrl).isEqualTo(result);
    }

    @Test
    @DisplayName("인증되지 않을 경우 유저의 프로필 사진을 수정하는 데 실패한다")
    void testFailToSetUserPicUrlIfUserIsNotAuthorized() {

        // given
        Long userNo = 1L;
        String newPicUrl = "https://test/new-img.png";

        doReturn(2L).when(userService).getCurrentUserNo();

        // when && then
        assertThatThrownBy(() -> userService.setUserPicUrl(userNo, newPicUrl))
                .isInstanceOf(RequestForbiddenException.class);
    }

    @Test
    @DisplayName("유저가 존재하지 않을 경우 유저의 프로필 사진을 수정하는 데 실패한다")
    void testFailToSetUserPicUrlIfUserIsNotExist() {

        // given
        Long userNo = 1L;
        String newPicUrl = "https://test/new-img.png";

        doReturn(userNo).when(userService).getCurrentUserNo();
        given(userRepository.findById(userNo))
                .willReturn(Optional.empty());

        // when && then
        assertThatThrownBy(() -> userService.setUserPicUrl(userNo, newPicUrl))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("존재하지 않는 유저입니다.");
    }

    @Test
    @DisplayName("채팅방 ID로 채팅방 내 모든 유저의 정보를 조회한다")
    void testSuccessToGetUserInfosByChatRoomNo() {

        // given
        Long chatRoomNo = 1L;

        ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
                .title("채팅방1")
                .build();

        UserEntity userEntity1 = createUserEntity(
                1L,
                "tester1",
                "password123",
                "무키무키",
                passwordEncoder,
                "https://test/img.png"
        );

        UserEntity userEntity2 = createUserEntity(
                2L,
                "tester2",
                "password123",
                "푸키푸키",
                passwordEncoder,
                "https://test/img.png"
        );

        UserChatRoomEntity userChatRoomEntity1 = UserChatRoomEntity.builder()
                .user(userEntity1)
                .chatRoom(chatRoomEntity)
                .build();

        UserChatRoomEntity userChatRoomEntity2 = UserChatRoomEntity.builder()
                .user(userEntity2)
                .chatRoom(chatRoomEntity)
                .build();

        given(userChatRoomRepository.findByNo(chatRoomNo))
                .willReturn(List.of(
                        userChatRoomEntity1,
                        userChatRoomEntity2
                ));

        // when
        List<UserInfoResponse> userInfoResponses = userService.getUserInfosByChatRoomNo(chatRoomNo);

        // then
        assertThat(userInfoResponses.get(0).getUserNo()).isEqualTo(1L);
        assertThat(userInfoResponses.get(0).getLoginId()).isEqualTo("tester1");
        assertThat(userInfoResponses.get(0).getNickname()).isEqualTo("무키무키");
        assertThat(userInfoResponses.get(1).getUserNo()).isEqualTo(2L);
        assertThat(userInfoResponses.get(1).getLoginId()).isEqualTo("tester2");
        assertThat(userInfoResponses.get(1).getNickname()).isEqualTo("푸키푸키");
    }
}