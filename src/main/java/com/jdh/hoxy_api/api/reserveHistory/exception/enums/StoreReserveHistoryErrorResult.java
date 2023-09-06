package com.jdh.hoxy_api.api.reserveHistory.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreReserveHistoryErrorResult {

    ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "SR0001", "예약 정보 내역이 이미 존재합니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
