package com.jdh.hoxy_api.api.reserve.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdh.hoxy_api.api.reserve.application.StoreReserveGetService;
import com.jdh.hoxy_api.api.reserve.application.impl.StoreReserveStateChgServiceImpl;
import com.jdh.hoxy_api.api.reserve.dto.request.StoreReserveStateChgRequestDTO;
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

import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class StoreReserveControllerStateChgDocsTest {

    @MockBean
    StoreReserveGetService storeReserveGetService;

    @MockBean
    StoreReserveStateChgServiceImpl storeReserveStateChgService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "1")
    public void storeReserveStateChgApi() throws Exception {
        // given
        final StoreReserveStateChgRequestDTO requestDTO = StoreReserveStateChgRequestDTO.builder()
                .reserveState("ACCEPT")
                .build();
        final String body = objectMapper.writeValueAsString(requestDTO);
        doNothing().when(storeReserveStateChgService).editStoreReserveState(1, ReserveState.ACCEPT, 1);

        // then
        mockMvc.perform(put("/manage/store/reserve/1").content(body).accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "accessToken")
                        .header("content-type", "application/json")
                )
                .andExpect(status().isOk())
                .andDo(document("edit-store-reserve-state",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("reserveState").description("상태 (ACCEPT: 접수, REJECT: 거절)")
                        ),
                        responseFields(
                                fieldWithPath("result").description("요청 결과"),
                                fieldWithPath("msg").description("메시지"),
                                fieldWithPath("data").description("결과")
                        )
                ));
    }

}
