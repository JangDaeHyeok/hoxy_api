package com.jdh.hoxy_api.api.store.domain.entity;

import com.jdh.hoxy_api.api.common.entity.DelYnEntity;
import com.jdh.hoxy_api.api.common.entity.RegModDtEntity;
import com.jdh.hoxy_api.api.reserve.domain.entity.StoreReserve;
import com.jdh.hoxy_api.api.store.exception.StoreAdminException;
import com.jdh.hoxy_api.api.store.exception.enums.StoreAdminErrorResult;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends RegModDtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    private String name;

    @Embedded
    private DelYnEntity delYn;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "idx")
    private StoreAdmin storeAdmin;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<StoreReserve> storeReserves = new ArrayList<>();

    public void addStoreAdmin(StoreAdmin storeAdmin) {
        verifyNotContainAdmin();
        setStoreAdmin(storeAdmin);
    }

    private void verifyNotContainAdmin() {
        if(this.storeAdmin != null)
            throw new StoreAdminException(StoreAdminErrorResult.DUPLICATE_STORE);
    }

    private void setStoreAdmin(StoreAdmin storeAdmin) {
        this.storeAdmin = storeAdmin;
    }

    @Builder
    protected Store(int idx, String name, List<StoreReserve> storeReserves) {
        this.idx = idx;
        this.name = name;

        if(storeReserves != null) this.storeReserves = storeReserves;
        else this.storeReserves = new ArrayList<>();

        this.delYn = DelYnEntity.getDelN();
    }
}
