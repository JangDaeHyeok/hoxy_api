package com.jdh.hoxy_api.api.reserveHistory.exception;

import com.jdh.hoxy_api.api.reserveHistory.exception.enums.StoreReserveHistoryErrorResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StoreReserveHistoryException extends RuntimeException {

    private final StoreReserveHistoryErrorResult storeReserveHistoryErrorResult;

}
