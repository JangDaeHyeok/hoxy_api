package com.jdh.hoxy_api.api.store.controller;

import com.jdh.hoxy_api.api.store.application.StoreGetService;
import com.jdh.hoxy_api.api.store.dto.response.StoreGetResponseDTO;
import com.jdh.hoxy_api.api.common.response.entity.ApiResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreGetService storeGetService;

    @GetMapping(value = "/store")
    public ResponseEntity<ApiResponseEntity> storeListGetApi() throws Exception {
        final List<StoreGetResponseDTO> result = storeGetService.getStoreList();

        return ApiResponseEntity.successResponseEntity(result);
    }

}
