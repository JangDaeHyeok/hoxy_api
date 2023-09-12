package com.jdh.hoxy_api.api.login.application;

import com.jdh.hoxy_api.api.login.dto.response.LoginResponseDTO;

public interface LoginService {

    LoginResponseDTO login(final String id, final String password) throws Exception ;

}
