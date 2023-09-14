package com.jdh.hoxy_api.api.reserveHistory.dto.response;

import com.jdh.hoxy_api.api.reserveHistory.domain.entity.StoreReserveHistory;
import com.jdh.hoxy_api.api.reserveHistory.enums.ReserveState;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Builder
public class StoreReserveHistoryGetResponseDTO {

    private final String name;

    private final String phone;

    private final int reserveNum;

    private final ReserveState reserveState;

    private final LocalDateTime reserveProcDt;

    public static StoreReserveHistoryGetResponseDTO of(StoreReserveHistory history) {
        return StoreReserveHistoryGetResponseDTO.builder()
                .name(history.getReserveHistoryInfo().getName())
                .phone(history.getReserveHistoryInfo().getPhone())
                .reserveNum(history.getReserveHistoryInfo().getReserveNum())
                .reserveState(history.getStoreReserveHistoryInfo().getReserveState())
                .reserveProcDt(history.getStoreReserveHistoryInfo().getReserveProcDt())
                .build();
    }

}
