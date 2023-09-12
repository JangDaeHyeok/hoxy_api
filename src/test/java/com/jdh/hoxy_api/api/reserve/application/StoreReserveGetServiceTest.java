package com.jdh.hoxy_api.api.reserve.application;

import com.jdh.hoxy_api.api.reserve.application.impl.StoreReserveGetServiceImpl;
import com.jdh.hoxy_api.api.reserve.domain.entity.ReserveInfo;
import com.jdh.hoxy_api.api.reserve.domain.entity.StoreReserve;
import com.jdh.hoxy_api.api.reserve.domain.repository.StoreReserveRepository;
import com.jdh.hoxy_api.api.reserve.dto.response.StoreReserveGetResponseDTO;
import com.jdh.hoxy_api.api.store.domain.entity.Store;
import com.jdh.hoxy_api.api.store.domain.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreReserveGetServiceTest {

    @InjectMocks
    StoreReserveGetServiceImpl target;

    @Mock
    StoreRepository storeRepository;

    @Test
    public void StoreReserveService_Not_Null() {
        assertThat(target).isNotNull();
    }

    @Test
    public void StoreReserveService_예약_목록_조회_성공() throws Exception {
        // given
        final Store store = getTestStore();
        final List<StoreReserve> resultList = getTestStoreReserveList(store);
        final Store resultStore = Store.builder()
                .name("테스트")
                .storeReserves(resultList)
                .build();
        when(storeRepository.findByIdWithStoreReserves(eq(1), any())).thenReturn(Optional.ofNullable(resultStore));

        // then
        final List<StoreReserveGetResponseDTO> result = target.getStoreReserveList(1);

        // when
        assertThat(result).isNotNull();
        assertThat(result.get(0).getName()).isEqualTo("테스트");
        assertThat(result.get(0).getPhone()).isEqualTo("01012341234");
        assertThat(result.get(0).getReserveNum()).isEqualTo(2);
        assertThat(result.get(1).getName()).isEqualTo("테스트2");
        assertThat(result.get(1).getPhone()).isEqualTo("01056785678");
        assertThat(result.get(1).getReserveNum()).isEqualTo(4);

        // verify
        verify(storeRepository, times(1)).findByIdWithStoreReserves(eq(1), any());
    }

    private Store getTestStore() {
        return Store.builder()
                .name("테스트")
                .build();
    }

    private List<StoreReserve> getTestStoreReserveList(Store store) {
        List<StoreReserve> reserveList = new ArrayList<>();

        final ReserveInfo reserveInfo1 = ReserveInfo.builder()
                .name("테스트")
                .phone("01012341234")
                .reserveNum(2)
                .build();
        final StoreReserve reserve1 = StoreReserve.builder()
                .store(store)
                .reserveInfo(reserveInfo1)
                .build();
        final ReserveInfo reserveInfo2 = ReserveInfo.builder()
                .name("테스트2")
                .phone("01056785678")
                .reserveNum(4)
                .build();
        final StoreReserve reserve2 = StoreReserve.builder()
                .store(store)
                .reserveInfo(reserveInfo2)
                .build();

        reserveList.add(reserve1);
        reserveList.add(reserve2);

        return reserveList;
    }

}
