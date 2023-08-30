package com.jdh.hoxy_api.api.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jdh.hoxy_api.api.store.application.impl.StoreGetServiceImpl;
import com.jdh.hoxy_api.api.store.dto.response.StoreGetResponseDTO;
import com.jdh.hoxy_api.api.common.response.entity.ApiResponseEntity;
import com.jdh.hoxy_api.config.security.TestSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
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
@ContextConfiguration(classes = {StoreController.class, ObjectMapper.class})
@WebAppConfiguration
@EnableWebMvc
@AutoConfigureMockMvc
@Import({TestSecurityConfig.class})
public class StoreControllerTest {

    @MockBean
    StoreGetServiceImpl storeGetService;

    @Autowired
    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init() {
        gson = new Gson();
    }

    @Test
    public void Store_조회_성공() throws Exception {
        // given
        final StoreGetResponseDTO store = StoreGetResponseDTO.builder()
                .idx(1)
                .name("테스트")
                .build();
        final List<StoreGetResponseDTO> resultList = new ArrayList<>(){{
            add(store);
        }};
        when(storeGetService.getStoreList()).thenReturn(resultList);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/store")
        );

        // then
        resultActions.andExpect(status().isOk());

        final ApiResponseEntity response = gson.fromJson(resultActions.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), ApiResponseEntity.class);
        final List<StoreGetResponseDTO> data = gson.fromJson(response.getData().toString(), new TypeToken<List<StoreGetResponseDTO>>(){}.getType());
        assertThat(data.get(0).getIdx()).isEqualTo(1);
        assertThat(data.get(0).getName()).isEqualTo("테스트");
    }

}
