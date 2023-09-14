package com.jdh.hoxy_api.api.reserveHistory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jdh.hoxy_api.api.common.response.entity.ApiResponseEntity;
import com.jdh.hoxy_api.api.reserveHistory.application.StoreReserveHistoryGetService;
import com.jdh.hoxy_api.api.reserveHistory.dto.response.StoreReserveHistoryGetResponseDTO;
import com.jdh.hoxy_api.api.reserveHistory.enums.ReserveState;
import com.jdh.hoxy_api.api.reserveHistory.exception.StoreReserveHistoryException;
import com.jdh.hoxy_api.api.reserveHistory.exception.enums.StoreReserveHistoryErrorResult;
import com.jdh.hoxy_api.config.exception.common.ApiExceptionEntity;
import com.jdh.hoxy_api.config.exception.storeReserveHistory.StoreReserveHistoryApiExceptionAdvice;
import com.jdh.hoxy_api.util.gson.LocalDateTimeAdapter;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {StoreReserveHistoryController.class, ObjectMapper.class, StoreReserveHistoryApiExceptionAdvice.class})
@WebAppConfiguration
@EnableWebMvc
@AutoConfigureMockMvc
public class StoreReserveHistoryControllerGetTest {

    @MockBean
    StoreReserveHistoryGetService storeReserveHistoryGetService;

    @Autowired
    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }

    @Test
    @DisplayName("StoreReserveHistory 목록 조회 사용자 미인증으로 인한 실패 테스트")
    @WithAnonymousUser
    public void StoreReserveHistory_목록_조회_실패_인증정보_없음() throws Exception {
        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/manage/store/reserve/history")
        );

        // then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("StoreReserveHistory 목록 조회 regDt 매개변수 형식 불일치로 인한 테스트 실패")
    @WithMockUser(username = "1")
    public void StoreReserveHistory_목록_조회_실패_regDt_형식_불일치() throws Exception {
        // given
        when(storeReserveHistoryGetService.getStoreReserveHistoryList(1, "20230914")).thenThrow(new StoreReserveHistoryException(StoreReserveHistoryErrorResult.INCORRECT_SEARCH_FORMAT_REG_DT));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/manage/store/reserve/history")
                        .param("regDt", "20230914")
        );

        // then
        final ApiExceptionEntity result = gson.fromJson(resultActions.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), ApiExceptionEntity.class);

        resultActions.andExpect(status().isBadRequest());
        assertThat(result.getErrorCode()).isEqualTo(StoreReserveHistoryErrorResult.INCORRECT_SEARCH_FORMAT_REG_DT.getCode());
        assertThat(result.getErrorMsg()).isEqualTo(StoreReserveHistoryErrorResult.INCORRECT_SEARCH_FORMAT_REG_DT.getMessage());
    }

    @Test
    @DisplayName("StoreReserveHistory 목록 조회 성공")
    @WithMockUser(username = "1")
    public void StoreReserveHistory_목록_조회_성공() throws Exception {
        // given
        final List<StoreReserveHistoryGetResponseDTO> result = getTestHistoryList();
        when(storeReserveHistoryGetService.getStoreReserveHistoryList(1, "2023-09-14")).thenReturn(result);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/manage/store/reserve/history")
                        .param("regDt", "2023-09-14")
        );

        // then
        final ApiResponseEntity response = gson.fromJson(resultActions.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), ApiResponseEntity.class);
        String jsonData = gson.toJson(response.getData());
        final List<StoreReserveHistoryGetResponseDTO> data = gson.fromJson(jsonData, new TypeToken<List<StoreReserveHistoryGetResponseDTO>>(){}.getType());

        resultActions.andExpect(status().isOk());
        assertThat(data).isNotNull();
        assertThat(data.get(0).getName()).isEqualTo("테스트");
        assertThat(data.get(0).getPhone()).isEqualTo("01012341234");
        assertThat(data.get(0).getReserveNum()).isEqualTo(2);
        assertThat(data.get(0).getReserveProcDt()).isEqualTo(LocalDateTime.parse("2023-09-14 10:15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        assertThat(data.get(0).getReserveState()).isEqualTo(ReserveState.ACCEPT);
        assertThat(data.get(1).getName()).isEqualTo("테스트2");
        assertThat(data.get(1).getPhone()).isEqualTo("01056785678");
        assertThat(data.get(1).getReserveNum()).isEqualTo(4);
        assertThat(data.get(1).getReserveProcDt()).isEqualTo(LocalDateTime.parse("2023-09-14 22:33:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        assertThat(data.get(1).getReserveState()).isEqualTo(ReserveState.REJECT);
    }

    private List<StoreReserveHistoryGetResponseDTO> getTestHistoryList() {
        List<StoreReserveHistoryGetResponseDTO> returnList = new ArrayList<>();

        final StoreReserveHistoryGetResponseDTO dto1 = StoreReserveHistoryGetResponseDTO.builder()
                .name("테스트")
                .phone("01012341234")
                .reserveNum(2)
                .reserveProcDt(LocalDateTime.of(2023, 9, 14, 10, 15))
                .reserveState(ReserveState.ACCEPT)
                .build();
        final StoreReserveHistoryGetResponseDTO dto2 = StoreReserveHistoryGetResponseDTO.builder()
                .name("테스트2")
                .phone("01056785678")
                .reserveNum(4)
                .reserveProcDt(LocalDateTime.of(2023, 9, 14, 22, 33))
                .reserveState(ReserveState.REJECT)
                .build();

        returnList.add(dto1);
        returnList.add(dto2);

        return returnList;
    }

}
