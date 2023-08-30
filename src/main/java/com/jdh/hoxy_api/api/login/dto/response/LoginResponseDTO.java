package com.jdh.hoxy_api.api.login.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class LoginResponseDTO {

    private final String token;

}
