package com.jdh.hoxy_api.api.reserve.dto.response;

import com.jdh.hoxy_api.api.reserve.domain.entity.StoreReserve;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@RequiredArgsConstructor
@Builder
public class StoreReserveGetResponseDTO {

    private final int idx;

    private final int storeIdx;

    private final String name;

    private final String phone;

    private final int reserveNum;

    private final String regDt;

    public static StoreReserveGetResponseDTO of(StoreReserve storeReserve) {
        return StoreReserveGetResponseDTO.builder()
                .idx(storeReserve.getIdx())
                .storeIdx(storeReserve.getStore().getIdx())
                .name(storeReserve.getReserveInfo().getName())
                .phone(storeReserve.getReserveInfo().getPhone())
                .reserveNum(storeReserve.getReserveInfo().getReserveNum())
                .regDt(storeReserve.getRegDt() != null ? storeReserve.getRegDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) : "")
                .build();
    }

}
