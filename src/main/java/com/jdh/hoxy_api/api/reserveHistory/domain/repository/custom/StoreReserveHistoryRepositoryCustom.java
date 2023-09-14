package com.jdh.hoxy_api.api.reserveHistory.domain.repository.custom;

import com.jdh.hoxy_api.api.reserveHistory.domain.entity.StoreReserveHistory;

import java.time.LocalDate;
import java.util.List;

public interface StoreReserveHistoryRepositoryCustom {

    List<StoreReserveHistory> getStoreReserveHistoryList(final int storeIdx, final LocalDate regDt);

}
