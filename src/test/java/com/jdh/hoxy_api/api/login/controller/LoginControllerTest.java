package com.jdh.hoxy_api.api.login.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jdh.hoxy_api.api.common.response.entity.ApiResponseEntity;
import com.jdh.hoxy_api.api.common.response.enums.ResponseEnum;
import com.jdh.hoxy_api.api.login.application.impl.LoginServiceImpl;
import com.jdh.hoxy_api.api.login.dto.request.LoginRequestDTO;
import com.jdh.hoxy_api.api.login.dto.response.LoginResponseDTO;
import com.jdh.hoxy_api.api.login.exception.LoginException;
import com.jdh.hoxy_api.api.login.exception.enums.LoginErrorResult;
import com.jdh.hoxy_api.config.exception.common.ApiExceptionEntity;
import com.jdh.hoxy_api.config.exception.login.LoginApiExceptionAdvice;
import com.jdh.hoxy_api.config.security.TestSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {LoginController.class, ObjectMapper.class})
@WebAppConfiguration
@EnableWebMvc
@AutoConfigureMockMvc
@Import({TestSecurityConfig.class})
public class LoginControllerTest {

    @MockBean
    LoginServiceImpl loginService;

    @Autowired
    MockMvc mockMvc;
    Gson gson;

    @BeforeEach
    public void init() {
        gson = new Gson();

        mockMvc = MockMvcBuilders.standaloneSetup(new LoginController(loginService))
                .setControllerAdvice(LoginApiExceptionAdvice.class)
                .build();
    }

    @Test
    @DisplayName("id 입력값 미존재로 인한 로그인 api 요청 실패")
    public void LoginController_실패_id_입력값_미존재() throws Exception {
        // given
        final LoginRequestDTO requestDTO = LoginRequestDTO.builder()
                .password("1234")
                .build();

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/manage/login")
                        .content(gson.toJson(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("비밀번호 입력값 미존재로 인한 로그인 api 요청 실패")
    public void LoginController_실패_비밀번호_입력값_미존재() throws Exception {
        // given
        final LoginRequestDTO requestDTO = LoginRequestDTO.builder()
                .id("test")
                .build();

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/manage/login")
                        .content(gson.toJson(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("아이디 혹은 비밀번호가 올바르지 않은 경우 로그인 api 요청 실패")
    public void LoginController_실패_아이디_혹은_비밀번호_불일치() throws Exception {
        // given
        final LoginRequestDTO requestDTO = LoginRequestDTO.builder()
                .id("test")
                .password("5678")
                .build();
        doThrow(new LoginException(LoginErrorResult.ID_PASSWORD_NOT_CORRECT))
                .when(loginService).login("test", "5678");

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/manage/login")
                        .content(gson.toJson(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        final ApiExceptionEntity responseEntity = gson.fromJson(resultActions.andReturn()
                .getResponse().getContentAsString(StandardCharsets.UTF_8), ApiExceptionEntity.class);

        // then
        resultActions.andExpect(status().isBadRequest());
        assertThat(responseEntity.getErrorCode()).isEqualTo(LoginErrorResult.ID_PASSWORD_NOT_CORRECT.getCode());
        assertThat(responseEntity.getErrorMsg()).isEqualTo(LoginErrorResult.ID_PASSWORD_NOT_CORRECT.getMessage());
    }

    @Test
    @DisplayName("로그인 api 요청 성공")
    public void LoginController_성공() throws Exception {
        // given
        final LoginRequestDTO requestDTO = LoginRequestDTO.builder()
                .id("test")
                .password("1234")
                .build();
        final LoginResponseDTO responseDTO = LoginResponseDTO.builder()
                .token("testToken1234")
                .build();
        when(loginService.login(requestDTO.getId(), requestDTO.getPassword())).thenReturn(responseDTO);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/manage/login")
                        .content(gson.toJson(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        final ApiResponseEntity responseEntity = gson.fromJson(resultActions.andReturn()
                .getResponse().getContentAsString(StandardCharsets.UTF_8), ApiResponseEntity.class);
        final LoginResponseDTO responseData = gson.fromJson(responseEntity.getData().toString(), LoginResponseDTO.class);

        // then
        resultActions.andExpect(status().isOk());
        assertThat(responseEntity.getResult()).isEqualTo(ResponseEnum.OK.getResult());
        assertThat(responseEntity.getMsg()).isEqualTo("요청 성공");
        assertThat(responseData.getToken()).isEqualTo("testToken1234");
    }

}
