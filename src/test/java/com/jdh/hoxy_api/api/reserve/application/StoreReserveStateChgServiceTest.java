package com.jdh.hoxy_api.api.reserve.application;

import com.jdh.hoxy_api.api.reserve.application.impl.StoreReserveStateChgServiceImpl;
import com.jdh.hoxy_api.api.reserve.domain.entity.ReserveInfo;
import com.jdh.hoxy_api.api.reserve.domain.entity.StoreReserve;
import com.jdh.hoxy_api.api.reserve.domain.repository.StoreReserveRepository;
import com.jdh.hoxy_api.api.reserve.exception.StoreReserveException;
import com.jdh.hoxy_api.api.reserve.exception.enums.StoreReserveErrorResult;
import com.jdh.hoxy_api.api.reserveHistory.domain.entity.StoreReserveHistory;
import com.jdh.hoxy_api.api.reserveHistory.domain.repository.StoreReserveHistoryRepository;
import com.jdh.hoxy_api.api.reserveHistory.enums.ReserveState;
import com.jdh.hoxy_api.api.reserveHistory.exception.StoreReserveHistoryException;
import com.jdh.hoxy_api.api.reserveHistory.exception.enums.StoreReserveHistoryErrorResult;
import com.jdh.hoxy_api.api.store.domain.entity.Store;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreReserveStateChgServiceTest {

    @InjectMocks
    StoreReserveStateChgServiceImpl target;

    @Mock
    StoreReserveRepository storeReserveRepository;

    @Mock
    StoreReserveHistoryRepository storeReserveHistoryRepository;

    @Test
    public void storeReserveStateChgServiceImpl_Not_Null() {
        assertThat(target).isNotNull();
    }

    @Test
    @DisplayName("StoreReserveStateChgService 예약 정보가 없는 경우 상태 변경 실패 테스트")
    public void storeReserveStateChgService_예약_정보가_없는_경우_실패_테스트() {
        // given
        when(storeReserveRepository.findById((long) 1)).thenReturn(Optional.empty());

        // when
        final StoreReserveException result = assertThrows(StoreReserveException.class, () -> target.editStoreReserveState(1, any(), 1));

        // then
        assertThat(result.getStoreReserveErrorResult()).isEqualTo(StoreReserveErrorResult.NOT_EXISTS);
    }

    @Test
    @DisplayName("StoreReserveStateChgService 예약 정보의 업체 정보가 로그인된 업체 정보와 다른 경우 상태 변경 실패 테스트")
    public void storeReserveStateChgService_업체_정보가_다른_경우_실패_테스트() {
        // given
        final Store store = Store.builder()
                .idx(2)
                .name("테스트")
                .build();
        final ReserveInfo reserveInfo = ReserveInfo.builder()
                .name("테스트")
                .phone("01012341234")
                .reserveNum(2)
                .build();
        final StoreReserve storeReserve = StoreReserve.builder()
                .store(store)
                .reserveInfo(reserveInfo)
                .build();
        when(storeReserveRepository.findById((long) 1)).thenReturn(Optional.of(storeReserve));

        // when
        final StoreReserveException result = assertThrows(StoreReserveException.class, () -> target.editStoreReserveState(1, any(), 1));

        // then
        assertThat(result.getStoreReserveErrorResult()).isEqualTo(StoreReserveErrorResult.NOT_THIS_STORE);
    }

    @Test
    @DisplayName("StoreReserveStateChgService 이미 삭제한 예약 정보를 삭제하려는 경우 상태 변경 실패 테스트")
    public void storeReserveStateChgService_이미_삭제한_예약_정보인_경우_실패_테스트() {
        // given
        final StoreReserve storeReserve = getTestStoreReserve();
        storeReserve.deleteReserveInfo();
        when(storeReserveRepository.findById((long) 1)).thenReturn(Optional.of(storeReserve));

        // when
        final StoreReserveException result = assertThrows(StoreReserveException.class, () -> target.editStoreReserveState(1, any(), 1));

        // then
        assertThat(result.getStoreReserveErrorResult()).isEqualTo(StoreReserveErrorResult.ALREADY_DELETE);
    }

    @Test
    @DisplayName("StoreReserveStateChgService 이미 접수 혹은 취소한 예약인 경우 상태 변경 실패 테스트")
    public void storeReserveStateChgService_이미_접수_혹은_취소한_예약_정보인_경우_실패_테스트() {
        // given
        final StoreReserve storeReserve = getTestStoreReserve();
        when(storeReserveRepository.findById((long) 1)).thenReturn(Optional.of(storeReserve));
        final StoreReserveHistory storeReserveHistory = StoreReserveHistory.acceptOf(storeReserve);
        when(storeReserveHistoryRepository.findByStoreReserveHistoryPKIdx(1)).thenReturn(Optional.of(storeReserveHistory));

        // when
        final StoreReserveHistoryException result = assertThrows(StoreReserveHistoryException.class, () -> target.editStoreReserveState(1, any(), 1));

        // then
        assertThat(result.getStoreReserveHistoryErrorResult()).isEqualTo(StoreReserveHistoryErrorResult.ALREADY_EXISTS);
    }

    @Test
    @DisplayName("StoreReserveStateChgService 상태 변경 성공 테스트")
    public void storeReserveStateChgService_성공_테스트() {
        // given
        final StoreReserve storeReserve = getTestStoreReserve();
        when(storeReserveRepository.findById((long) 1)).thenReturn(Optional.of(storeReserve));
        when(storeReserveHistoryRepository.findByStoreReserveHistoryPKIdx(1)).thenReturn(Optional.empty());

        // when
        target.editStoreReserveState(1, ReserveState.ACCEPT, 1);

        // then

        // verify
        verify(storeReserveRepository, times(1)).findById((long) 1);
        verify(storeReserveHistoryRepository, times(1)).findByStoreReserveHistoryPKIdx(1);
        verify(storeReserveRepository, times(1)).save(any(StoreReserve.class));
        verify(storeReserveHistoryRepository, times(1)).save(any(StoreReserveHistory.class));
    }

    private StoreReserve getTestStoreReserve() {

        final Store store = getTestStore();
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

    private Store getTestStore() {
        return Store.builder()
                .idx(1)
                .name("테스트")
                .build();
    }
}
