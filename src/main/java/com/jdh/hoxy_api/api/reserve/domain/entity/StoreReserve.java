package com.jdh.hoxy_api.api.reserve.domain.entity;

import com.jdh.hoxy_api.api.common.entity.DelYnEntity;
import com.jdh.hoxy_api.api.common.entity.RegModDtEntity;
import com.jdh.hoxy_api.api.reserve.exception.StoreReserveException;
import com.jdh.hoxy_api.api.reserve.exception.enums.StoreReserveErrorResult;
import com.jdh.hoxy_api.api.store.domain.entity.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreReserve extends RegModDtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @ManyToOne
    @JoinColumn(name = "store_idx")
    private Store store;

    @Embedded
    private ReserveInfo reserveInfo;

    @Embedded
    private DelYnEntity delYn;

    public void deleteReserveInfo() {
        // 이미 삭제된 예약 정보인 경우
        if(this.delYn.getDelYn().equals(DelYnEntity.getDelY().getDelYn())) {
            throw new StoreReserveException(StoreReserveErrorResult.ALREADY_DELETE);
        }

        this.delYn = DelYnEntity.getDelY();
    }

    @Builder
    public StoreReserve(Store store, ReserveInfo reserveInfo) {
        this.store = store;
        this.reserveInfo = reserveInfo;
        this.delYn = DelYnEntity.getDelN();
    }
}
