package com.jdh.hoxy_api.api.login.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDTO {

    @NotNull
    @NotBlank
    private String id;

    @NotNull
    @NotBlank
    private String password;

    @Builder
    public LoginRequestDTO(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
