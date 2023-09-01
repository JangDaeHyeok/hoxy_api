package com.jdh.hoxy_api.api.reserve.application;

import com.jdh.hoxy_api.api.reserve.dto.response.StoreReserveGetResponseDTO;

import java.util.List;

public interface StoreReserveGetService {

    List<StoreReserveGetResponseDTO> getStoreReserveList(final int storeIdx);

}
