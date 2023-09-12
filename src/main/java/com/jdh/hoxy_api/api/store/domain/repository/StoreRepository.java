package com.jdh.hoxy_api.api.store.domain.repository;

import com.jdh.hoxy_api.api.common.entity.DelYnEntity;
import com.jdh.hoxy_api.api.store.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByStoreAdminId(String storeAdminId);

    @Query("select s from Store s join fetch s.storeReserves r where s.idx = :id and s.delYn = :delYn")
    Optional<Store> findByIdWithStoreReserves(int id, DelYnEntity delYn);

    int countByStoreAdminId(String storeAdminId);

}
