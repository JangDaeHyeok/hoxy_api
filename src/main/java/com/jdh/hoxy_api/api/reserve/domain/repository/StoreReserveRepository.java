package com.jdh.hoxy_api.api.reserve.domain.repository;

import com.jdh.hoxy_api.api.common.entity.DelYnEntity;
import com.jdh.hoxy_api.api.reserve.domain.entity.StoreReserve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreReserveRepository extends JpaRepository<StoreReserve, Long> {

    List<StoreReserve> findByStoreIdxAndDelYn(int storeIdx, DelYnEntity delYn);

}
