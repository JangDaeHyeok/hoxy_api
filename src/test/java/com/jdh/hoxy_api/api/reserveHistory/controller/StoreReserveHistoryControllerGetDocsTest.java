package com.jdh.hoxy_api.api.reserveHistory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdh.hoxy_api.api.reserveHistory.application.StoreReserveHistoryGetService;
import com.jdh.hoxy_api.api.reserveHistory.dto.response.StoreReserveHistoryGetResponseDTO;
import com.jdh.hoxy_api.api.reserveHistory.enums.ReserveState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class StoreReserveHistoryControllerGetDocsTest {

    @MockBean
    StoreReserveHistoryGetService storeReserveHistoryGetService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "1")
    public void storeReserveHistoryGetApi() throws Exception {
        // given
        final List<StoreReserveHistoryGetResponseDTO> result = getTestHistoryList();
        when(storeReserveHistoryGetService.getStoreReserveHistoryList(1, "2023-09-14")).thenReturn(result);

        // then
        mockMvc.perform(get("/manage/store/reserve/history")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "accessToken")
                        .param("regDt", "2023-09-14"))
                .andExpect(status().isOk())
                .andDo(document("get-store-reserve-histories",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("regDt").description("검색 일자 yyyy-MM-dd (필수)")
                        ),
                        responseFields(
                                fieldWithPath("result").description("요청 결과"),
                                fieldWithPath("msg").description("메시지"),
                                fieldWithPath("data[].name").description("예약자명"),
                                fieldWithPath("data[].phone").description("예약자 전화번호"),
                                fieldWithPath("data[].reserveNum").description("예약 인원수"),
                                fieldWithPath("data[].reserveState").description("예약 상태"),
                                fieldWithPath("data[].reserveProcDt").description("예약 처리일자")

                        )
                ));
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
