package com.jdh.hoxy_api.config.exception.storeAdmin;

import com.jdh.hoxy_api.api.store.exception.StoreAdminException;
import com.jdh.hoxy_api.config.exception.common.ApiExceptionEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StoreAdminApiExceptionAdvice {

    @ExceptionHandler({StoreAdminException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest req, final StoreAdminException e) {
        // e.printStackTrace();
        return ResponseEntity
                .status(e.getStoreAdminErrorResult().getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(e.getStoreAdminErrorResult().getCode())
                        .errorMsg(e.getStoreAdminErrorResult().getMessage())
                        .build());
    }

}
