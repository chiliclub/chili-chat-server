package com.chiliclub.chilichat.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Size(min = 1, max = 30)
    @Column(name = "id", unique = true, length = 30)
    private String loginId;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "pwd", length = 30)
    private String password;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(length = 10, unique = true)
    private String nickname;

    private String picUrl;
}
