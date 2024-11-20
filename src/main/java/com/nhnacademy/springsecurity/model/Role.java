package com.nhnacademy.springsecurity.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {

    ADMIN,
    MEMBER,
    GOOGLE;

    @JsonCreator
    public static Role fromString(String value) {
        if (value == null) {
            return null;
        }
        return Role.valueOf(value.trim().toUpperCase()); // 대소문자 구분 없이 처리
    }

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }
}
