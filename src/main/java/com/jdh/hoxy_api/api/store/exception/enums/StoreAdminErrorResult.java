package com.jdh.hoxy_api.api.store.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreAdminErrorResult {

    STORE_NOT_EXIST(HttpStatus.BAD_REQUEST, "SA0001", "존재하지 않는 업체입니다."),
    DUPLICATE_STORE(HttpStatus.BAD_REQUEST, "SA0002", "이미 가입한 업체입니다."),
    DUPLICATE_STORE_ADMIN_ID(HttpStatus.BAD_REQUEST, "SA0003", "중복되는 사용자 ID 입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
