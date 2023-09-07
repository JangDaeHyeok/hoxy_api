package com.jdh.hoxy_api.api.reserve.application.impl;

import com.jdh.hoxy_api.api.reserve.application.StoreReserveStateChgService;
import com.jdh.hoxy_api.api.reserve.domain.entity.StoreReserve;
import com.jdh.hoxy_api.api.reserve.domain.repository.StoreReserveRepository;
import com.jdh.hoxy_api.api.reserve.exception.StoreReserveException;
import com.jdh.hoxy_api.api.reserve.exception.enums.StoreReserveErrorResult;
import com.jdh.hoxy_api.api.reserveHistory.domain.entity.StoreReserveHistory;
import com.jdh.hoxy_api.api.reserveHistory.domain.repository.StoreReserveHistoryRepository;
import com.jdh.hoxy_api.api.reserveHistory.enums.ReserveState;
import com.jdh.hoxy_api.api.reserveHistory.exception.StoreReserveHistoryException;
import com.jdh.hoxy_api.api.reserveHistory.exception.enums.StoreReserveHistoryErrorResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreReserveStateChgServiceImpl implements StoreReserveStateChgService {

    private final StoreReserveRepository storeReserveRepository;

    private final StoreReserveHistoryRepository storeReserveHistoryRepository;

    @Override
    @Transactional
    public void editStoreReserveState(final int idx, final ReserveState reserveState, final int storeIdx) {
        // 업체 예약 정보 조회
        StoreReserve storeReserve = storeReserveRepository.findById((long) idx)
                .orElseThrow(() -> new StoreReserveException(StoreReserveErrorResult.NOT_EXISTS));

        // 접근 가능한 업체 정보인지 확인
        if(storeIdx != storeReserve.getStore().getIdx())
            throw new StoreReserveException(StoreReserveErrorResult.NOT_THIS_STORE);

        // 업체 예약 정보 삭제
        storeReserve.deleteReserveInfo();

        // 업체 예약 이력 정보 조회
        StoreReserveHistory findStoreReserveHistory = storeReserveHistoryRepository.findByStoreReserveHistoryPKIdx(idx).orElse(null);

        // 업체 예약 이력 정보가 이미 존재하는 경우
        if(findStoreReserveHistory != null)
            throw new StoreReserveHistoryException(StoreReserveHistoryErrorResult.ALREADY_EXISTS);

        // 업체 예약 이력 객체 생성
        StoreReserveHistory storeReserveHistory;
        if(reserveState.equals(ReserveState.ACCEPT))
            storeReserveHistory = StoreReserveHistory.acceptOf(storeReserve);
        else
            storeReserveHistory = StoreReserveHistory.rejectOf(storeReserve);

        // 업체 예약 정보 수정
        storeReserveRepository.save(storeReserve);
        // 업체 예약 이력 정보 저장
        storeReserveHistoryRepository.save(storeReserveHistory);
    }
}
