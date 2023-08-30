package com.jdh.hoxy_api.api.login.exception;

import com.jdh.hoxy_api.api.login.exception.enums.LoginErrorResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginException extends RuntimeException {

    private final LoginErrorResult loginErrorResult;

}
