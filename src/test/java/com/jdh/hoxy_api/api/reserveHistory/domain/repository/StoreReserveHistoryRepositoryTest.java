package com.jdh.hoxy_api.api.reserveHistory.domain.repository;

import com.jdh.hoxy_api.api.reserve.domain.entity.ReserveInfo;
import com.jdh.hoxy_api.api.reserve.domain.entity.StoreReserve;
import com.jdh.hoxy_api.api.reserve.domain.repository.StoreReserveRepository;
import com.jdh.hoxy_api.api.reserveHistory.domain.entity.StoreReserveHistory;
import com.jdh.hoxy_api.api.reserveHistory.enums.ReserveState;
import com.jdh.hoxy_api.api.store.domain.entity.Store;
import com.jdh.hoxy_api.api.store.domain.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StoreReserveHistoryRepositoryTest {

    @Autowired
    StoreReserveHistoryRepository storeReserveHistoryRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    StoreReserveRepository storeReserveRepository;

    @Test
    @DisplayName("StoreReserveHistory 수락 조회 테스트")
    public void StoreReserveHistory_수락_조회() {
        // given
        final Store store = getTestStore();
        storeRepository.save(store);
        final StoreReserve storeReserve = getTestStoreReserve(store);
        storeReserveRepository.save(storeReserve);
        final StoreReserveHistory storeReserveHistory = StoreReserveHistory.acceptOf(storeReserve);
        storeReserveHistoryRepository.save(storeReserveHistory);

        // when
        final StoreReserveHistory result = storeReserveHistoryRepository.findByStoreReserveHistoryPKIdx(storeReserveHistory.getStoreReserveHistoryPK().getIdx()).get();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getStoreReserveHistoryPK().getIdx()).isEqualTo(storeReserve.getIdx());
        assertThat(result.getStoreReserveHistoryPK().getStoreIdx()).isEqualTo(storeReserve.getStore().getIdx());
        assertThat(result.getReserveHistoryInfo().getName()).isEqualTo(storeReserve.getReserveInfo().getName());
        assertThat(result.getReserveHistoryInfo().getPhone()).isEqualTo(storeReserve.getReserveInfo().getPhone());
        assertThat(result.getReserveHistoryInfo().getReserveNum()).isEqualTo(storeReserve.getReserveInfo().getReserveNum());
        assertThat(result.getStoreReserveHistoryInfo().getReserveState()).isEqualTo(ReserveState.ACCEPT);
    }

    @Test
    @DisplayName("StoreReserveHistory 거절 조회 테스트")
    public void StoreReserveHistory_거절_조회() {
        // given
        final Store store = getTestStore();
        storeRepository.save(store);
        final StoreReserve storeReserve = getTestStoreReserve(store);
        storeReserveRepository.save(storeReserve);
        final StoreReserveHistory storeReserveHistory = StoreReserveHistory.rejectOf(storeReserve);
        storeReserveHistoryRepository.save(storeReserveHistory);

        // when
        final StoreReserveHistory result = storeReserveHistoryRepository.findByStoreReserveHistoryPKIdx(storeReserveHistory.getStoreReserveHistoryPK().getIdx()).get();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getStoreReserveHistoryPK().getIdx()).isEqualTo(storeReserve.getIdx());
        assertThat(result.getStoreReserveHistoryPK().getStoreIdx()).isEqualTo(storeReserve.getStore().getIdx());
        assertThat(result.getReserveHistoryInfo().getName()).isEqualTo(storeReserve.getReserveInfo().getName());
        assertThat(result.getReserveHistoryInfo().getPhone()).isEqualTo(storeReserve.getReserveInfo().getPhone());
        assertThat(result.getReserveHistoryInfo().getReserveNum()).isEqualTo(storeReserve.getReserveInfo().getReserveNum());
        assertThat(result.getStoreReserveHistoryInfo().getReserveState()).isEqualTo(ReserveState.REJECT);
    }

    private Store getTestStore() {
        return Store.builder()
                .name("테스트")
                .build();
    }

    private StoreReserve getTestStoreReserve(Store store) {
        final ReserveInfo reserveInfo = ReserveInfo.builder()
                .name("테스트")
                .phone("01012341234")
                .reserveNum(2)
                .build();

        return StoreReserve.builder()
                .store(store)
                .reserveInfo(reserveInfo)
                .build();
    }

}
