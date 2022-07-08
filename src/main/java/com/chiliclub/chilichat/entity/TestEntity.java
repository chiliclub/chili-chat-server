package com.chiliclub.chilichat.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "test")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;
}
