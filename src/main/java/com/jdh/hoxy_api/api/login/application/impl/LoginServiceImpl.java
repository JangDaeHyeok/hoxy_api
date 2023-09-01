package com.jdh.hoxy_api.api.login.application.impl;

import com.jdh.hoxy_api.api.login.application.LoginService;
import com.jdh.hoxy_api.api.login.dto.response.LoginResponseDTO;
import com.jdh.hoxy_api.api.login.exception.LoginException;
import com.jdh.hoxy_api.api.login.exception.enums.LoginErrorResult;
import com.jdh.hoxy_api.api.store.domain.entity.StoreAdmin;
import com.jdh.hoxy_api.api.store.domain.repository.StoreRepository;
import com.jdh.hoxy_api.config.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final StoreRepository storeRepository;

    private final PasswordEncoder bCryptPasswordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginResponseDTO login(String id, String password) {
        // StoreAdmin 정보 조회
        StoreAdmin findStoreAdmin = storeRepository.findByStoreAdminId(id)
                .orElseThrow(() -> new LoginException(LoginErrorResult.ID_PASSWORD_NOT_CORRECT))
                .getStoreAdmin();

        // 비밀번호 일치 여부 체크
        if(!findStoreAdmin.checkPassword(password, bCryptPasswordEncoder))
            throw new LoginException(LoginErrorResult.ID_PASSWORD_NOT_CORRECT);

        // jwt 토큰 생성
        String accessToken = jwtTokenProvider.generateAccessToken(String.valueOf(findStoreAdmin.getIdx()));

        return LoginResponseDTO.builder()
                .token(accessToken)
                .build();
    }
}
