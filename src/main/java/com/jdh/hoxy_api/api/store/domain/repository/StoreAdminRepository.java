package com.jdh.hoxy_api.api.store.domain.repository;

import com.jdh.hoxy_api.api.store.domain.entity.Store;
import com.jdh.hoxy_api.api.store.domain.entity.StoreAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreAdminRepository extends JpaRepository<StoreAdmin, Long> {

    StoreAdmin findByStore(Store store);

    StoreAdmin findByIdAndPassword(String id, String password);

}
