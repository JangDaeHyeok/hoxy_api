package com.jdh.hoxy_api.api.reserve.application.impl;

import com.jdh.hoxy_api.api.common.entity.DelYnEntity;
import com.jdh.hoxy_api.api.common.enums.YorN;
import com.jdh.hoxy_api.api.reserve.application.StoreReserveGetService;
import com.jdh.hoxy_api.api.reserve.domain.entity.StoreReserve;
import com.jdh.hoxy_api.api.reserve.domain.repository.StoreReserveRepository;
import com.jdh.hoxy_api.api.reserve.dto.response.StoreReserveGetResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreReserveGetServiceImpl implements StoreReserveGetService {

    private final StoreReserveRepository storeReserveRepository;

    @Override
    public List<StoreReserveGetResponseDTO> getStoreReserveList(final int storeIdx) {
        DelYnEntity delYn = DelYnEntity.getDelN();
        List<StoreReserve> result = storeReserveRepository.findByStoreIdxAndDelYn(storeIdx, delYn);

        return result.stream()
                .map(StoreReserveGetResponseDTO::of)
                .toList();
    }
}
