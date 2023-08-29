package com.jdh.hoxy_api.api.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jdh.hoxy_api.api.common.response.entity.ApiResponseEntity;
import com.jdh.hoxy_api.api.common.response.enums.ResponseEnum;
import com.jdh.hoxy_api.api.store.application.impl.StoreAdminAddServiceImpl;
import com.jdh.hoxy_api.api.store.dto.request.StoreAdminAddRequestDTO;
import com.jdh.hoxy_api.api.store.exception.StoreAdminException;
import com.jdh.hoxy_api.api.store.exception.enums.StoreAdminErrorResult;
import com.jdh.hoxy_api.config.exception.common.ApiExceptionEntity;
import com.jdh.hoxy_api.config.exception.storeAdmin.StoreAdminApiExceptionAdvice;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {StoreAdminController.class, ObjectMapper.class})
@WebAppConfiguration
@EnableWebMvc
@AutoConfigureMockMvc
@Import({TestSecurityConfig.class})
public class StoreAdminControllerTest {

    @MockBean
    StoreAdminAddServiceImpl storeAdminAddService;

    @Autowired
    MockMvc mockMvc;
    Gson gson;

    @BeforeEach
    public void init() {
        gson = new Gson();

        mockMvc = MockMvcBuilders.standaloneSetup(new StoreAdminController(storeAdminAddService))
                .setControllerAdvice(StoreAdminApiExceptionAdvice.class)
                .build();
    }

    @Test
    @DisplayName("업체 idx 입력값 미존재로 인한 회원가입 api 요청 실패")
    public void StoreAdmin_실패_업체_idx_입력값_미존재() throws Exception {
        // given
        final StoreAdminAddRequestDTO requestDTO = StoreAdminAddRequestDTO.builder()
                .id("test")
                .password("1234")
                .name("테스트")
                .build();

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/store/admin")
                        .content(gson.toJson(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("업체 관리자 id 입력값 미존재로 인한 회원가입 api 요청 실패")
    public void StoreAdmin_실패_업체_관리자_id_입력값_미존재() throws Exception {
        // given
        final StoreAdminAddRequestDTO requestDTO = StoreAdminAddRequestDTO.builder()
                .storeIdx(1)
                .password("1234")
                .name("테스트")
                .build();

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/store/admin")
                        .content(gson.toJson(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("업체 관리자 비밀번호 입력값 미존재로 인한 회원가입 api 요청 실패")
    public void StoreAdmin_실패_업체_관리자_비밀번호_입력값_미존재() throws Exception {
        // given
        final StoreAdminAddRequestDTO requestDTO = StoreAdminAddRequestDTO.builder()
                .storeIdx(1)
                .id("test")
                .name("테스트")
                .build();

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/store/admin")
                        .content(gson.toJson(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("업체 관리자 닉네임 입력값 미존재로 인한 회원가입 api 요청 실패")
    public void StoreAdmin_실패_업체_관리자_닉네임_입력값_미존재() throws Exception {
        // given
        final StoreAdminAddRequestDTO requestDTO = StoreAdminAddRequestDTO.builder()
                .storeIdx(1)
                .id("test")
                .password("1234")
                .build();

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/store/admin")
                        .content(gson.toJson(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("업체 관리자 존재하는 업체가 아닌 경우 회원가입 api 요청 실패")
    public void StoreAdmin_실패_존재하지_않는_업체() throws Exception {
        // given
        final StoreAdminAddRequestDTO requestDTO = getRequestDTO();
        doThrow(new StoreAdminException(StoreAdminErrorResult.STORE_NOT_EXIST))
                .when(storeAdminAddService)
                .addStoreAdmin(requestDTO.getStoreIdx(), requestDTO.getId(), requestDTO.getPassword(), requestDTO.getName());

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/store/admin")
                        .content(gson.toJson(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        final ApiExceptionEntity responseEntity = gson.fromJson(resultActions.andReturn()
                .getResponse().getContentAsString(StandardCharsets.UTF_8), ApiExceptionEntity.class);

        // then
        resultActions.andExpect(status().isBadRequest());
        assertThat(responseEntity.getErrorCode()).isEqualTo(StoreAdminErrorResult.STORE_NOT_EXIST.getCode());
        assertThat(responseEntity.getErrorMsg()).isEqualTo(StoreAdminErrorResult.STORE_NOT_EXIST.getMessage());
    }

    @Test
    @DisplayName("업체 관리자 계정을 이미 생성한 경우 회원가입 api 요청 실패")
    public void StoreAdmin_실패_이미_관리자_계정을_생성한_업체() throws Exception {
        // given
        final StoreAdminAddRequestDTO requestDTO = getRequestDTO();
        doThrow(new StoreAdminException(StoreAdminErrorResult.DUPLICATE_STORE))
                .when(storeAdminAddService)
                .addStoreAdmin(requestDTO.getStoreIdx(), requestDTO.getId(), requestDTO.getPassword(), requestDTO.getName());

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/store/admin")
                        .content(gson.toJson(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        final ApiExceptionEntity responseEntity = gson.fromJson(resultActions.andReturn()
                .getResponse().getContentAsString(StandardCharsets.UTF_8), ApiExceptionEntity.class);

        // then
        resultActions.andExpect(status().isBadRequest());
        assertThat(responseEntity.getErrorCode()).isEqualTo(StoreAdminErrorResult.DUPLICATE_STORE.getCode());
        assertThat(responseEntity.getErrorMsg()).isEqualTo(StoreAdminErrorResult.DUPLICATE_STORE.getMessage());
    }

    @Test
    @DisplayName("업체 관리자 이미 존재하는 계정인 경우 회원가입 api 요청 실패")
    public void StoreAdmin_실패_이미_존재하는_계정() throws Exception {
        // given
        final StoreAdminAddRequestDTO requestDTO = getRequestDTO();
        doThrow(new StoreAdminException(StoreAdminErrorResult.DUPLICATE_STORE_ADMIN_ID))
                .when(storeAdminAddService)
                .addStoreAdmin(requestDTO.getStoreIdx(), requestDTO.getId(), requestDTO.getPassword(), requestDTO.getName());

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/store/admin")
                        .content(gson.toJson(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        final ApiExceptionEntity responseEntity = gson.fromJson(resultActions.andReturn()
                .getResponse().getContentAsString(StandardCharsets.UTF_8), ApiExceptionEntity.class);

        // then
        resultActions.andExpect(status().isBadRequest());
        assertThat(responseEntity.getErrorCode()).isEqualTo(StoreAdminErrorResult.DUPLICATE_STORE_ADMIN_ID.getCode());
        assertThat(responseEntity.getErrorMsg()).isEqualTo(StoreAdminErrorResult.DUPLICATE_STORE_ADMIN_ID.getMessage());
    }

    @Test
    @DisplayName("업체 관리자 회원가입 api 요청 성공")
    public void StoreAdmin_성공() throws Exception {
        // given
        final StoreAdminAddRequestDTO requestDTO = getRequestDTO();
        doNothing()
                .when(storeAdminAddService)
                .addStoreAdmin(requestDTO.getStoreIdx(), requestDTO.getId(), requestDTO.getPassword(), requestDTO.getName());

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/store/admin")
                        .content(gson.toJson(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        final ApiResponseEntity responseEntity = gson.fromJson(resultActions.andReturn()
                .getResponse().getContentAsString(StandardCharsets.UTF_8), ApiResponseEntity.class);

        // then
        resultActions.andExpect(status().isOk());
        assertThat(responseEntity.getResult()).isEqualTo(ResponseEnum.OK.getResult());
        assertThat(responseEntity.getMsg()).isEqualTo("요청 성공");
    }

    private StoreAdminAddRequestDTO getRequestDTO() {
        return StoreAdminAddRequestDTO.builder()
                .storeIdx(1)
                .id("test")
                .password("1234")
                .name("테스트")
                .build();
    }

}
