package com.jdh.hoxy_api.api.reserve.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreReserveErrorResult {

    NOT_EXISTS(HttpStatus.BAD_REQUEST, "SR0001", "이미 처리한 예약 정보입니다."),
    ALREADY_DELETE(HttpStatus.BAD_REQUEST, "SR0002", "이미 삭제된 예약 정보입니다."),
    NOT_THIS_STORE(HttpStatus.BAD_REQUEST, "SR0003", "접근 가능한 예약 정보가 아닙니다. (업체 불일치)");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
