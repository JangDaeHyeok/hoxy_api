package com.jdh.hoxy_api.api.store.application.impl;

import com.jdh.hoxy_api.api.store.application.StoreAdminAddService;
import com.jdh.hoxy_api.api.store.domain.entity.Store;
import com.jdh.hoxy_api.api.store.domain.entity.StoreAdmin;
import com.jdh.hoxy_api.api.store.domain.repository.StoreRepository;
import com.jdh.hoxy_api.api.store.exception.StoreAdminException;
import com.jdh.hoxy_api.api.store.exception.enums.StoreAdminErrorResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreAdminAddServiceImpl implements StoreAdminAddService {

    private final StoreRepository storeRepository;

    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void addStoreAdmin(final int storeIdx, final String id, final String password, final String name) throws Exception {
        // 존재하는 업체가 아닌 경우
        Store store = storeRepository.findById((long) storeIdx)
                .orElseThrow(() -> new StoreAdminException(StoreAdminErrorResult.STORE_NOT_EXIST));

        // 중복되는 id인지 체크
        int idCnt = storeRepository.countByStoreAdminId(id);
        if(idCnt > 0)
            throw new StoreAdminException(StoreAdminErrorResult.DUPLICATE_STORE_ADMIN_ID);

        // 업체 관리자 계정 Entity 객체 생성
        StoreAdmin storeAdmin = StoreAdmin.builder()
                .id(id)
                .password(password)
                .name(name)
                .build();

        // 비밀번호 암호화
        storeAdmin.encryptPassword(bCryptPasswordEncoder);

        // Store Aggregate에 StoreAdmin 정보 등록
        store.addStoreAdmin(storeAdmin);

        // 업체 관리자 계정정보 저장
        storeRepository.save(store);
    }
}
