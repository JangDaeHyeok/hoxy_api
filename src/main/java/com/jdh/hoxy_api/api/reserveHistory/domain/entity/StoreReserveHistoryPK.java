package com.jdh.hoxy_api.api.reserveHistory.domain.entity;

import com.jdh.hoxy_api.api.reserve.domain.entity.StoreReserve;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Embeddable
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreReserveHistoryPK implements Serializable {

    private int idx;

    private int storeIdx;

    private LocalDateTime regDt;

    public StoreReserveHistoryPK of(StoreReserve storeReserve) {
        return StoreReserveHistoryPK.builder()
                .idx(storeReserve.getIdx())
                .storeIdx(storeReserve.getStore().getIdx())
                .regDt(storeReserve.getRegDt())
                .build();
    }

}
