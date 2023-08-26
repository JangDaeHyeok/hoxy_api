package com.jdh.hoxy_api.api.store.application.impl;

import com.jdh.hoxy_api.api.store.application.StoreGetService;
import com.jdh.hoxy_api.api.store.domain.entity.Store;
import com.jdh.hoxy_api.api.store.domain.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreGetServiceImpl implements StoreGetService {

    private final StoreRepository storeRepository;

    @Override
    public List<Store> getStoreList() {
        return storeRepository.findAll();
    }
}
