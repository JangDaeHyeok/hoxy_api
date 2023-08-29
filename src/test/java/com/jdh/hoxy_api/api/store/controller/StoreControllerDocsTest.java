package com.jdh.hoxy_api.api.store.controller;

import com.jdh.hoxy_api.api.store.application.impl.StoreGetServiceImpl;
import com.jdh.hoxy_api.api.store.dto.response.StoreGetResponseDTO;
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
public class StoreControllerDocsTest {

    @MockBean
    StoreGetServiceImpl storeGetService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void storeListGetApi() throws Exception {
        // given
        final StoreGetResponseDTO store = StoreGetResponseDTO.builder()
                .name("테스트")
                .build();
        final List<StoreGetResponseDTO> resultList = new ArrayList<>(){{
            add(store);
        }};
        when(storeGetService.getStoreList()).thenReturn(resultList);

        // then
        mockMvc.perform(get("/store").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("get-stores",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("result").description("요청 결과"),
                                fieldWithPath("msg").description("메시지"),
                                fieldWithPath("data[].name").description("업체명")
                        )
                ));
    }

}
