package com.jdh.hoxy_api.api.store.controller;

import com.jdh.hoxy_api.api.common.response.entity.ApiResponseEntity;
import com.jdh.hoxy_api.api.store.application.StoreAdminAddService;
import com.jdh.hoxy_api.api.store.dto.request.StoreAdminAddRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreAdminController {

    private final StoreAdminAddService storeAdminAddService;

    @PostMapping(value = "/manage/store/admin")
    public ResponseEntity<ApiResponseEntity> storeListGetApi(@RequestBody @Valid StoreAdminAddRequestDTO reqDTO) {
        storeAdminAddService.addStoreAdmin(reqDTO.getStoreIdx(), reqDTO.getId(), reqDTO.getPassword(), reqDTO.getName());

        return ApiResponseEntity.successResponseEntity();
    }

}
