package com.jdh.hoxy_api.config.exception.login;

import com.jdh.hoxy_api.api.login.exception.LoginException;
import com.jdh.hoxy_api.config.exception.common.ApiExceptionEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LoginApiExceptionAdvice {

    @ExceptionHandler({LoginException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest req, final LoginException e) {
        // e.printStackTrace();
        return ResponseEntity
                .status(e.getLoginErrorResult().getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(e.getLoginErrorResult().getCode())
                        .errorMsg(e.getLoginErrorResult().getMessage())
                        .build());
    }

}
