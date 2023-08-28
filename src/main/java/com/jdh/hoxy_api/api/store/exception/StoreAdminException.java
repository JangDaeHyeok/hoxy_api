package com.jdh.hoxy_api.api.store.exception;

import com.jdh.hoxy_api.api.store.exception.enums.StoreAdminErrorResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StoreAdminException extends RuntimeException {

    private final StoreAdminErrorResult storeAdminErrorResult;

}
