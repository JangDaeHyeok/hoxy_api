package com.jdh.hoxy_api.api.login.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdh.hoxy_api.api.login.application.impl.LoginServiceImpl;
import com.jdh.hoxy_api.api.login.dto.request.LoginRequestDTO;
import com.jdh.hoxy_api.api.login.dto.response.LoginResponseDTO;
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

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class LoginControllerDocsTest {

    @MockBean
    LoginServiceImpl loginService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void LoginApi() throws Exception {
        // given
        final LoginRequestDTO requestDTO = LoginRequestDTO.builder()
                .id("test")
                .password("1234")
                .build();
        final String body = objectMapper.writeValueAsString(requestDTO);

        final LoginResponseDTO responseDTO = LoginResponseDTO.builder()
                .token("testToken1234")
                .build();
        when(loginService.login(requestDTO.getId(), requestDTO.getPassword())).thenReturn(responseDTO);

        // then
        mockMvc.perform(post("/manage/login").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("store-admin-login",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").description("업체 관리자 ID"),
                                fieldWithPath("password").description("업체 관리자 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("result").description("요청 결과"),
                                fieldWithPath("msg").description("메시지"),
                                fieldWithPath("data").description("결과"),
                                fieldWithPath("data.token").description("JWT Access Token")
                        )
                ));
    }

}
