package com.jdh.hoxy_api.api.store.application;

import com.jdh.hoxy_api.api.store.dto.response.StoreGetResponseDTO;

import java.util.List;

public interface StoreGetService {

    List<StoreGetResponseDTO> getStoreList();

}
