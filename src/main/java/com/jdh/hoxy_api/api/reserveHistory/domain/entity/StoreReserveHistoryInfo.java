package com.jdh.hoxy_api.api.reserveHistory.domain.entity;

import com.jdh.hoxy_api.api.reserveHistory.enums.ReserveState;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreReserveHistoryInfo {

    @Enumerated(EnumType.STRING)
    private ReserveState reserveState;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime reserveProcDt;

    @Builder
    public StoreReserveHistoryInfo(ReserveState reserveState) {
        this.reserveState = reserveState;
    }
}
