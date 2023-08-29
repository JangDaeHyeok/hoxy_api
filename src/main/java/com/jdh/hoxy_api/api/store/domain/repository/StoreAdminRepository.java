package com.jdh.hoxy_api.api.store.domain.repository;

import com.jdh.hoxy_api.api.store.domain.entity.Store;
import com.jdh.hoxy_api.api.store.domain.entity.StoreAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreAdminRepository extends JpaRepository<StoreAdmin, Long> {

    Optional<StoreAdmin> findByStore(Store store);

    Optional<StoreAdmin> findByStoreIdx(int StoreIdx);

    int countById(String id);

}
