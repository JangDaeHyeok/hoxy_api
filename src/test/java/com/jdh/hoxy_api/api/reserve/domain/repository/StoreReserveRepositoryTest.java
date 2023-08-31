package com.jdh.hoxy_api.api.reserve.domain.repository;

import com.jdh.hoxy_api.api.reserve.domain.entity.ReserveInfo;
import com.jdh.hoxy_api.api.reserve.domain.entity.StoreReserve;
import com.jdh.hoxy_api.api.store.domain.entity.Store;
import com.jdh.hoxy_api.api.store.domain.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StoreReserveRepositoryTest {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    StoreReserveRepository storeReserveRepository;

    @Test
    public void StoreReserveRepository_Not_Null() {
        assertThat(storeReserveRepository).isNotNull();
    }

    @Test
    public void StoreReserveRepository_예약_목록_조회() {
        // given
        final Store store = getTestStore();
        storeRepository.save(store);

        final ReserveInfo reserveInfo1 = ReserveInfo.builder()
                .name("테스트")
                .phone("01012341234")
                .reserveNum(2)
                .build();
        final StoreReserve reserve1 = StoreReserve.builder()
                .store(store)
                .reserveInfo(reserveInfo1)
                .build();
        storeReserveRepository.save(reserve1);

        final ReserveInfo reserveInfo2 = ReserveInfo.builder()
                .name("테스트2")
                .phone("01056785678")
                .reserveNum(4)
                .build();
        final StoreReserve reserve2 = StoreReserve.builder()
                .store(store)
                .reserveInfo(reserveInfo2)
                .build();
        storeReserveRepository.save(reserve2);

        // when
        final List<StoreReserve> findList = storeReserveRepository.findByStoreIdx(store.getIdx());

        // then
        assertThat(findList).isNotNull();
        assertThat(findList.get(0).getReserveInfo().getName()).isEqualTo("테스트");
        assertThat(findList.get(0).getReserveInfo().getPhone()).isEqualTo("01012341234");
        assertThat(findList.get(0).getReserveInfo().getReserveNum()).isEqualTo(2);
        assertThat(findList.get(1).getReserveInfo().getName()).isEqualTo("테스트2");
        assertThat(findList.get(1).getReserveInfo().getPhone()).isEqualTo("01056785678");
        assertThat(findList.get(1).getReserveInfo().getReserveNum()).isEqualTo(4);
    }

    private Store getTestStore() {
        return Store.builder()
                .name("테스트")
                .build();
    }

}
