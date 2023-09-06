package com.jdh.hoxy_api.api.reserve.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreReserveErrorResult {

    NOT_EXISTS(HttpStatus.BAD_REQUEST, "SR0001", "존재하지 않는 예약 정보입니다."),
    ALREADY_DELETE(HttpStatus.BAD_REQUEST, "SR0002", "이미 삭제된 예약 정보입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
