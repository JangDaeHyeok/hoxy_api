package com.jdh.hoxy_api.api.reserve.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jdh.hoxy_api.api.reserve.application.impl.StoreReserveGetServiceImpl;
import com.jdh.hoxy_api.api.reserve.application.impl.StoreReserveStateChgServiceImpl;
import com.jdh.hoxy_api.api.reserve.dto.request.StoreReserveStateChgRequestDTO;
import com.jdh.hoxy_api.api.reserveHistory.enums.ReserveState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {StoreReserveController.class, ObjectMapper.class})
@WebAppConfiguration
@EnableWebMvc
@AutoConfigureMockMvc
public class StoreReserveControllerStateChgTest {

    @MockBean
    StoreReserveGetServiceImpl storeReserveGetService;

    @MockBean
    StoreReserveStateChgServiceImpl storeReserveStateChgService;

    @Autowired
    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init() {
        gson = new Gson();
    }

    @Test
    @DisplayName("StoreReserve 상태 변경 사용자 미인증으로 인한 실패 테스트")
    @WithAnonymousUser
    public void StoreReserve_상태_변경_실패_인증정보_없음() throws Exception {
        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/manage/store/reserve/1").with(csrf())
        );

        // then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("StoreReserve 상태 변경 변경 상태값 미존재로 인한 실패 테스트")
    @WithMockUser(username = "1")
    public void StoreReserve_상태_변경_실패_변경_상태값_Null() throws Exception {
        // given
        final StoreReserveStateChgRequestDTO requestDTO = StoreReserveStateChgRequestDTO.builder().build();

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/manage/store/reserve/1")
                        .with(csrf())
                        .content(gson.toJson(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("StoreReserve 상태 변경 변경 상태값 불일치로 인한 실패 테스트")
    @WithMockUser(username = "1")
    public void StoreReserve_상태_변경_실패_변경_상태값_불일치() throws Exception {
        // given
        final StoreReserveStateChgRequestDTO requestDTO = StoreReserveStateChgRequestDTO.builder()
                .reserveState("test")
                .build();

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/manage/store/reserve/1")
                        .with(csrf())
                        .content(gson.toJson(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("StoreReserve 상태 변경 성공")
    @WithMockUser(username = "1")
    public void StoreReserve_상태_변경_성공() throws Exception {
        // given
        final StoreReserveStateChgRequestDTO requestDTO = StoreReserveStateChgRequestDTO.builder()
                .reserveState("ACCEPT")
                .build();
        doNothing().when(storeReserveStateChgService)
                .editStoreReserveState(1, ReserveState.ACCEPT, 1);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/manage/store/reserve/1")
                        .with(csrf())
                        .content(gson.toJson(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk());
    }

}
