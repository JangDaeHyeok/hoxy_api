package com.jdh.hoxy_api.api.reserve.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jdh.hoxy_api.api.common.response.entity.ApiResponseEntity;
import com.jdh.hoxy_api.api.reserve.application.StoreReserveGetService;
import com.jdh.hoxy_api.api.reserve.dto.response.StoreReserveGetResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {StoreReserveController.class, ObjectMapper.class})
@WebAppConfiguration
@EnableWebMvc
@AutoConfigureMockMvc
public class StoreReserveControllerGetTest {

    @MockBean
    StoreReserveGetService storeReserveGetService;

    @Autowired
    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init() {
        gson = new Gson();
    }

    @Test
    @DisplayName("StoreReserve 사용자 미인증으로 인한 조회 실패 테스트")
    @WithAnonymousUser
    public void StoreReserve_조회_실패_인증정보_없음() throws Exception {
        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/manage/store/reserve")
        );

        // then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("StoreReserve 조회 성공 테스트")
    @WithMockUser(username = "1")
    public void StoreReserve_조회_성공() throws Exception {
        // given
        final List<StoreReserveGetResponseDTO> resultList = getTestStoreReserveGetResponseDTOList();
        when(storeReserveGetService.getStoreReserveList(1)).thenReturn(resultList);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/manage/store/reserve")
        );

        // then
        final ApiResponseEntity response = gson.fromJson(resultActions.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), ApiResponseEntity.class);
        final List<StoreReserveGetResponseDTO> data = gson.fromJson(response.getData().toString(), new TypeToken<List<StoreReserveGetResponseDTO>>(){}.getType());

        resultActions.andExpect(status().isOk());
        assertThat(data).isNotNull();
        assertThat(data.get(0).getIdx()).isEqualTo(1);
        assertThat(data.get(0).getStoreIdx()).isEqualTo(1);
        assertThat(data.get(0).getName()).isEqualTo("테스트");
        assertThat(data.get(0).getPhone()).isEqualTo("01012341234");
        assertThat(data.get(0).getReserveNum()).isEqualTo(2);
        assertThat(data.get(1).getIdx()).isEqualTo(2);
        assertThat(data.get(1).getStoreIdx()).isEqualTo(1);
        assertThat(data.get(1).getName()).isEqualTo("테스트2");
        assertThat(data.get(1).getPhone()).isEqualTo("01056785678");
        assertThat(data.get(1).getReserveNum()).isEqualTo(4);
    }

    private List<StoreReserveGetResponseDTO> getTestStoreReserveGetResponseDTOList() {
        final List<StoreReserveGetResponseDTO> reserveList = new ArrayList<>();

        final StoreReserveGetResponseDTO response1 = StoreReserveGetResponseDTO.builder()
                .idx(1)
                .storeIdx(1)
                .name("테스트")
                .phone("01012341234")
                .reserveNum(2)
                .build();
        final StoreReserveGetResponseDTO response2 = StoreReserveGetResponseDTO.builder()
                .idx(2)
                .storeIdx(1)
                .name("테스트2")
                .phone("01056785678")
                .reserveNum(4)
                .build();

        reserveList.add(response1);
        reserveList.add(response2);

        return reserveList;
    }

}
