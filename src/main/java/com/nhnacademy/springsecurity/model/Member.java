package com.nhnacademy.springsecurity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    private String id;
    private String name;
    private String password;
    private Integer age;
    private Role role;
}