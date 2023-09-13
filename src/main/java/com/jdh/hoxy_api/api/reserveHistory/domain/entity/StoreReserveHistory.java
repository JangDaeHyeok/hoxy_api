package com.jdh.hoxy_api.api.reserveHistory.domain.entity;

import com.jdh.hoxy_api.api.reserve.domain.entity.StoreReserve;
import com.jdh.hoxy_api.api.reserveHistory.enums.ReserveState;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreReserveHistory {

    @EmbeddedId
    private StoreReserveHistoryPK storeReserveHistoryPK;

    @Embedded
    private ReserveHistoryInfo reserveHistoryInfo;

    @Embedded
    private StoreReserveHistoryInfo storeReserveHistoryInfo;

    public static StoreReserveHistory acceptOf(StoreReserve storeReserve) {
        return of(storeReserve, ReserveState.ACCEPT);
    }

    public static StoreReserveHistory rejectOf(StoreReserve storeReserve) {
        return of(storeReserve, ReserveState.REJECT);
    }

    private static StoreReserveHistory of(StoreReserve storeReserve, ReserveState reserveState) {
        StoreReserveHistoryPK pk = getStoreReserveHistoryPK(storeReserve);

        ReserveHistoryInfo info = getReserveHistoryInfo(storeReserve);

        StoreReserveHistoryInfo historyInfo = StoreReserveHistoryInfo.builder()
                .reserveState(reserveState)
                .build();

        return StoreReserveHistory.builder()
                .storeReserveHistoryPK(pk)
                .reserveHistoryInfo(info)
                .storeReserveHistoryInfo(historyInfo)
                .build();
    }

    private static StoreReserveHistoryPK getStoreReserveHistoryPK(StoreReserve storeReserve) {
        return StoreReserveHistoryPK.builder()
                .idx(storeReserve.getIdx())
                .storeIdx(storeReserve.getStore().getIdx())
                .regDt(storeReserve.getRegDt() != null ? storeReserve.getRegDt().toLocalDate() : null)
                .build();
    }

    private static ReserveHistoryInfo getReserveHistoryInfo(StoreReserve storeReserve) {
        return ReserveHistoryInfo.builder()
                .name(storeReserve.getReserveInfo().getName())
                .phone(storeReserve.getReserveInfo().getPhone())
                .reserveNum(storeReserve.getReserveInfo().getReserveNum())
                .build();
    }
}
