package com.jdh.hoxy_api.api.store.domain.entity;

import com.jdh.hoxy_api.api.common.entity.DelYnEntity;
import com.jdh.hoxy_api.api.common.entity.RegModDtEntity;
import com.jdh.hoxy_api.api.common.enums.YorN;
import com.jdh.hoxy_api.api.store.exception.StoreAdminException;
import com.jdh.hoxy_api.api.store.exception.enums.StoreAdminErrorResult;
import jakarta.persistence.*;
import lombok.*;

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

    @OneToOne(mappedBy = "store", cascade = CascadeType.ALL)
    private StoreAdmin storeAdmin;

    public void addStoreAdmin(StoreAdmin storeAdmin) {
        if(this.storeAdmin != null)
            throw new StoreAdminException(StoreAdminErrorResult.DUPLICATE_STORE);

        this.storeAdmin = storeAdmin;
    }

    @Builder
    protected Store(String name) {
        this.name = name;
        this.delYn = DelYnEntity.builder()
                .delYn(YorN.N)
                .build();
    }
}
