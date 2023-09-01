package com.jdh.hoxy_api.api.reserve.controller;

import com.jdh.hoxy_api.api.common.response.entity.ApiResponseEntity;
import com.jdh.hoxy_api.api.reserve.application.StoreReserveGetService;
import com.jdh.hoxy_api.api.reserve.dto.response.StoreReserveGetResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class StoreReserveController {

    private final StoreReserveGetService storeReserveGetService;

    @GetMapping(value = "/manage/store/reserve")
    public ResponseEntity<ApiResponseEntity> storeReserveGetApi(Authentication authentication) {
        List<StoreReserveGetResponseDTO> result = storeReserveGetService.getStoreReserveList(Integer.parseInt(authentication.getName()));

        return ApiResponseEntity.successResponseEntity(result);
    }

}
