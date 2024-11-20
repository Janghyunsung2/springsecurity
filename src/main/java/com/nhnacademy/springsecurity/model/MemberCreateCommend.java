package com.nhnacademy.springsecurity.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateCommend {

    private String id;
    private String name;
    private String password;
    private Integer age;
    private Role role;
}
