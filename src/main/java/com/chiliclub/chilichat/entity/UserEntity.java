package com.chiliclub.chilichat.entity;

import com.chiliclub.chilichat.model.user.UserSaveRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long no;

    @NotNull
    @Size(min = 5, max = 30)
    @Column(name = "id", unique = true, length = 30)
    private String loginId;

    @NotNull
    @Column(name = "pwd")
    private String password;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(length = 10, unique = true)
    private String nickname;

    private String picUrl;

    @Builder
    public UserEntity(String loginId, String password, String nickname, String picUrl) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.picUrl = picUrl;
    }

    public void updatePicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public static UserEntity create(
            UserSaveRequest req,
            PasswordEncoder passwordEncoder,
            String defaultPicUrl
    ) {

        return UserEntity.builder()
                .loginId(req.getId())
                .password(passwordEncoder.encode(req.getPassword()))
                .nickname(req.getNickname())
                .picUrl(defaultPicUrl)
                .build();
    }
}
