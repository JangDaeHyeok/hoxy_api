package com.jdh.hoxy_api.config.exception.storeReserveHistory;

import com.jdh.hoxy_api.api.reserveHistory.exception.StoreReserveHistoryException;
import com.jdh.hoxy_api.config.exception.common.ApiExceptionEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StoreReserveHistoryApiExceptionAdvice {

    @ExceptionHandler({StoreReserveHistoryException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest req, final StoreReserveHistoryException e) {
        // e.printStackTrace();
        return ResponseEntity
                .status(e.getStoreReserveHistoryErrorResult().getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(e.getStoreReserveHistoryErrorResult().getCode())
                        .errorMsg(e.getStoreReserveHistoryErrorResult().getMessage())
                        .build());
    }

}
