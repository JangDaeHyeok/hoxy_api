package com.jdh.hoxy_api.config.exception.storeReserve;

import com.jdh.hoxy_api.api.reserve.exception.StoreReserveException;
import com.jdh.hoxy_api.config.exception.common.ApiExceptionEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StoreReserveApiExceptionAdvice {

    @ExceptionHandler({StoreReserveException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest req, final StoreReserveException e) {
        // e.printStackTrace();
        return ResponseEntity
                .status(e.getStoreReserveErrorResult().getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(e.getStoreReserveErrorResult().getCode())
                        .errorMsg(e.getStoreReserveErrorResult().getMessage())
                        .build());
    }

}
