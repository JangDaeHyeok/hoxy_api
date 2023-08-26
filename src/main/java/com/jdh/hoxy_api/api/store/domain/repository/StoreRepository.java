package com.jdh.hoxy_api.api.store.domain.repository;

import com.jdh.hoxy_api.api.store.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}
