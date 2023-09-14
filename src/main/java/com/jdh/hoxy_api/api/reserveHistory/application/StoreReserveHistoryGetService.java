package com.jdh.hoxy_api.api.reserveHistory.application;

import com.jdh.hoxy_api.api.reserveHistory.dto.response.StoreReserveHistoryGetResponseDTO;

import java.util.List;

public interface StoreReserveHistoryGetService {

    List<StoreReserveHistoryGetResponseDTO> getStoreReserveHistoryList(final int idx, final String regDtStr) throws Exception;

}
