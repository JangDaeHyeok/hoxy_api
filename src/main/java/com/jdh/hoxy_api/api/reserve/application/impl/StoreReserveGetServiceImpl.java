package com.jdh.hoxy_api.api.reserve.application.impl;

import com.jdh.hoxy_api.api.common.entity.DelYnEntity;
import com.jdh.hoxy_api.api.reserve.application.StoreReserveGetService;
import com.jdh.hoxy_api.api.reserve.domain.entity.StoreReserve;
import com.jdh.hoxy_api.api.reserve.dto.response.StoreReserveGetResponseDTO;
import com.jdh.hoxy_api.api.store.domain.entity.Store;
import com.jdh.hoxy_api.api.store.domain.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreReserveGetServiceImpl implements StoreReserveGetService {

    private final StoreRepository storeRepository;

    @Override
    public List<StoreReserveGetResponseDTO> getStoreReserveList(final int storeIdx) {
        DelYnEntity delYn = DelYnEntity.getDelN();

        Store find = storeRepository.findByIdWithStoreReserves(storeIdx, delYn).orElse(Store.builder().build());
        List<StoreReserve> result = find.getStoreReserves();

        return result.stream()
                .map(StoreReserveGetResponseDTO::of)
                .toList();
    }
}
