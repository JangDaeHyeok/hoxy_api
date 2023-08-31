package com.jdh.hoxy_api.api.reserve.domain.entity;

import com.jdh.hoxy_api.api.common.entity.DelYnEntity;
import com.jdh.hoxy_api.api.common.entity.RegModDtEntity;
import com.jdh.hoxy_api.api.common.enums.YorN;
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
    @JoinColumn(name = "store_idx", referencedColumnName = "idx")
    private Store store;

    @Embedded
    private ReserveInfo reserveInfo;

    @Embedded
    private DelYnEntity delYn;

    @Builder
    public StoreReserve(Store store, ReserveInfo reserveInfo) {
        this.store = store;
        this.reserveInfo = reserveInfo;
        this.delYn = DelYnEntity.builder()
                .delYn(YorN.N)
                .build();
    }
}
