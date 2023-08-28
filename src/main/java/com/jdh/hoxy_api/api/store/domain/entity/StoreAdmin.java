package com.jdh.hoxy_api.api.store.domain.entity;

import com.jdh.hoxy_api.api.common.entity.DelYnEntity;
import com.jdh.hoxy_api.api.common.entity.RegModDtEntity;
import com.jdh.hoxy_api.api.common.enums.YorN;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreAdmin extends RegModDtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idx;

    private String id;

    private String password;

    private String pwSalt;

    private String name;

    @Embedded
    private DelYnEntity delYn;

    @OneToOne
    @JoinColumn(name = "store_idx")
    private Store store;

    public void chgDelYn(YorN yorN) {
        this.delYn = DelYnEntity.builder()
                .delYn(yorN)
                .build();
    }

    public void addStore(Store store) {
        this.store = store;
    }

    @Builder
    protected StoreAdmin(String id, String password, String pwSalt, String name) {
        this.id = id;
        this.password = password;
        this.pwSalt = pwSalt;
        this.name = name;
        this.delYn = DelYnEntity.builder()
                .delYn(YorN.N)
                .build();
    }

}
