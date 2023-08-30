package com.jdh.hoxy_api.api.login.controller;

import com.jdh.hoxy_api.api.common.response.entity.ApiResponseEntity;
import com.jdh.hoxy_api.api.login.application.LoginService;
import com.jdh.hoxy_api.api.login.dto.request.LoginRequestDTO;
import com.jdh.hoxy_api.api.login.dto.response.LoginResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping(value = "/manage/login")
    public ResponseEntity<ApiResponseEntity> login(@RequestBody @Valid LoginRequestDTO reqDTO) {
        LoginResponseDTO result = loginService.login(reqDTO.getId(), reqDTO.getPassword());

        return ApiResponseEntity
                .successResponseEntity(result);
    }

}
