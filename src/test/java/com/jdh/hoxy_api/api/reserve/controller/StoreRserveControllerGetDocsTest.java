package com.jdh.hoxy_api.api.reserve.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdh.hoxy_api.api.reserve.application.StoreReserveGetService;
import com.jdh.hoxy_api.api.reserve.dto.response.StoreReserveGetResponseDTO;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class StoreRserveControllerGetDocsTest {

    @MockBean
    StoreReserveGetService storeReserveGetService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "1")
    public void storeReserveGetApi() throws Exception {
        // given
        final List<StoreReserveGetResponseDTO> resultList = getTestStoreReserveGetResponseDTOList();
        when(storeReserveGetService.getStoreReserveList(1)).thenReturn(resultList);

        // then
        mockMvc.perform(get("/manage/store/reserve").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("get-store-reserves",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("result").description("요청 결과"),
                                fieldWithPath("msg").description("메시지"),
                                fieldWithPath("data[].idx").description("예약 IDX"),
                                fieldWithPath("data[].storeIdx").description("업체 IDX"),
                                fieldWithPath("data[].name").description("예약자명"),
                                fieldWithPath("data[].phone").description("예약자 전화번호"),
                                fieldWithPath("data[].reserveNum").description("예약 인원수"),
                                fieldWithPath("data[].regDt").description("예약 신청일자")

                        )
                ));
    }

    private List<StoreReserveGetResponseDTO> getTestStoreReserveGetResponseDTOList() {
        final List<StoreReserveGetResponseDTO> reserveList = new ArrayList<>();

        final StoreReserveGetResponseDTO response1 = StoreReserveGetResponseDTO.builder()
                .idx(1)
                .storeIdx(1)
                .name("테스트")
                .phone("01012341234")
                .reserveNum(2)
                .regDt("2023-09-01 00:00:00")
                .build();
        final StoreReserveGetResponseDTO response2 = StoreReserveGetResponseDTO.builder()
                .idx(2)
                .storeIdx(1)
                .name("테스트2")
                .phone("01056785678")
                .reserveNum(4)
                .regDt("2023-09-01 14:08:27")
                .build();

        reserveList.add(response1);
        reserveList.add(response2);

        return reserveList;
    }

}
