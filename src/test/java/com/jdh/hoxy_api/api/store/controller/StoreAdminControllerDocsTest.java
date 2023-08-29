package com.jdh.hoxy_api.api.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdh.hoxy_api.api.store.application.impl.StoreAdminAddServiceImpl;
import com.jdh.hoxy_api.api.store.dto.request.StoreAdminAddRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class StoreAdminControllerDocsTest {

    @MockBean
    StoreAdminAddServiceImpl storeAdminAddService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void storeAdminAddApi() throws Exception {
        // given
        final StoreAdminAddRequestDTO requestDTO = getRequestDTO();
        final String body = objectMapper.writeValueAsString(requestDTO);

        // then
        mockMvc.perform(post("/store/admin").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("add-store-admin",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("storeIdx").description("업체 IDX"),
                                fieldWithPath("id").description("업체 관리자 ID"),
                                fieldWithPath("password").description("업체 관리자 비밀번호"),
                                fieldWithPath("name").description("업체 관리자 닉네임")
                        ),
                        responseFields(
                                fieldWithPath("result").description("요청 결과"),
                                fieldWithPath("msg").description("메시지"),
                                fieldWithPath("data").description("결과")
                        )
                ));
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
