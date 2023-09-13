package com.jdh.hoxy_api.api.reserveHistory.domain.repository;

import com.jdh.hoxy_api.api.reserveHistory.domain.entity.StoreReserveHistory;
import com.jdh.hoxy_api.api.reserveHistory.domain.repository.custom.StoreReserveHistoryRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.jdh.hoxy_api.api.reserveHistory.domain.entity.QStoreReserveHistory.storeReserveHistory;

@RequiredArgsConstructor
@Repository
public class StoreReserveHistoryRepositoryImpl implements StoreReserveHistoryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StoreReserveHistory> getStoreReserveHistoryList(int idx, LocalDate regDt) {
        return jpaQueryFactory
                .selectFrom(storeReserveHistory)
                .where(storeReserveHistory.storeReserveHistoryPK.idx.eq(idx), storeReserveHistory.storeReserveHistoryPK.regDt.eq(regDt))
                .orderBy(storeReserveHistory.storeReserveHistoryPK.idx.desc())
                .fetch();
    }
}
