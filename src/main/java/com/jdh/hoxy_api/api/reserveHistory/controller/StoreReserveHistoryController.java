package com.jdh.hoxy_api.api.reserveHistory.controller;

import com.jdh.hoxy_api.api.common.response.entity.ApiResponseEntity;
import com.jdh.hoxy_api.api.reserveHistory.application.StoreReserveHistoryGetService;
import com.jdh.hoxy_api.api.reserveHistory.dto.response.StoreReserveHistoryGetResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreReserveHistoryController {

    private final StoreReserveHistoryGetService storeReserveHistoryGetService;

    @GetMapping(value = "/manage/store/reserve/history")
    public ResponseEntity<ApiResponseEntity> storeReserveHistoryGetApi(Authentication authentication, @RequestParam(defaultValue = "") String regDt) throws Exception {
        if(regDt.isBlank()) regDt = LocalDate.now().toString();

        List<StoreReserveHistoryGetResponseDTO> result = storeReserveHistoryGetService.getStoreReserveHistoryList(Integer.parseInt(authentication.getName()), regDt);

        return ApiResponseEntity.successResponseEntity(result);
    }

}
