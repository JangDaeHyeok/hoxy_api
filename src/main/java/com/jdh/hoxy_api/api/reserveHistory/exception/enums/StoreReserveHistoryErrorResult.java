package com.jdh.hoxy_api.api.reserveHistory.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreReserveHistoryErrorResult {

    ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "SR0001", "예약 정보 내역이 이미 존재합니다."),
    INCORRECT_SEARCH_FORMAT_REG_DT(HttpStatus.BAD_REQUEST, "SR0002", "검색 날짜 형식이 일치하지 않습니다. (yyyy-MM-dd)");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
