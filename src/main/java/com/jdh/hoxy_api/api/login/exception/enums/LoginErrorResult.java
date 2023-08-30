package com.jdh.hoxy_api.api.login.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LoginErrorResult {

    ID_PASSWORD_NOT_CORRECT(HttpStatus.BAD_REQUEST, "L0001", "아이디나 비밀번호가 올바르지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
