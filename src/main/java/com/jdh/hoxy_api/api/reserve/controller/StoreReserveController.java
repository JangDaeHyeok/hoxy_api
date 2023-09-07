package com.jdh.hoxy_api.api.reserve.controller;

import com.jdh.hoxy_api.api.common.response.entity.ApiResponseEntity;
import com.jdh.hoxy_api.api.reserve.application.StoreReserveGetService;
import com.jdh.hoxy_api.api.reserve.application.StoreReserveStateChgService;
import com.jdh.hoxy_api.api.reserve.dto.request.StoreReserveStateChgRequestDTO;
import com.jdh.hoxy_api.api.reserve.dto.response.StoreReserveGetResponseDTO;
import com.jdh.hoxy_api.api.reserveHistory.enums.ReserveState;
import com.jdh.hoxy_api.common.annotations.groups.ValidationSequence;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreReserveController {

    private final StoreReserveGetService storeReserveGetService;

    private final StoreReserveStateChgService storeReserveStateChgService;

    @GetMapping(value = "/manage/store/reserve")
    public ResponseEntity<ApiResponseEntity> storeReserveGetApi(Authentication authentication) {
        List<StoreReserveGetResponseDTO> result = storeReserveGetService.getStoreReserveList(Integer.parseInt(authentication.getName()));

        return ApiResponseEntity.successResponseEntity(result);
    }

    @PutMapping(value = "/manage/store/reserve/{idx}")
    public ResponseEntity<ApiResponseEntity> storeReserveStateChgApi(@PathVariable int idx, @RequestBody @Validated(ValidationSequence.class) StoreReserveStateChgRequestDTO requestDTO
            , Authentication authentication) {
        ReserveState reserveState = ReserveState.valueOf(requestDTO.getReserveState());
        storeReserveStateChgService.editStoreReserveState(idx, reserveState, Integer.parseInt(authentication.getName()));

        return ApiResponseEntity.successResponseEntity();
    }

}
