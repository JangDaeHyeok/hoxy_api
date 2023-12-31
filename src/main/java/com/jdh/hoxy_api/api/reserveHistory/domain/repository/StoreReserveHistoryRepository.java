package com.jdh.hoxy_api.api.reserveHistory.domain.repository;

import com.jdh.hoxy_api.api.reserveHistory.domain.entity.StoreReserveHistory;
import com.jdh.hoxy_api.api.reserveHistory.domain.repository.custom.StoreReserveHistoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreReserveHistoryRepository extends JpaRepository<StoreReserveHistory, Long>, StoreReserveHistoryRepositoryCustom {

    Optional<StoreReserveHistory> findByStoreReserveHistoryPKIdx(int idx);

}
