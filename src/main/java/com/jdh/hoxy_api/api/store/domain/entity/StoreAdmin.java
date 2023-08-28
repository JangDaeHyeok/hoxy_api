package com.jdh.hoxy_api.api.store.domain.entity;

import com.jdh.hoxy_api.api.common.entity.RegModDtEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToOne
    @JoinColumn(name = "store_idx")
    private Store store;

    public void addStore(Store store) {
        this.store = store;
    }

    @Builder
    protected StoreAdmin(String id, String password, String pwSalt, String name) {
        this.id = id;
        this.password = password;
        this.pwSalt = pwSalt;
        this.name = name;
    }

}
