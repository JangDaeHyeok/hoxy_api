package com.jdh.hoxy_api.api.reserve.exception;

import com.jdh.hoxy_api.api.reserve.exception.enums.StoreReserveErrorResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StoreReserveException extends RuntimeException {

    private final StoreReserveErrorResult storeReserveErrorResult;

}
