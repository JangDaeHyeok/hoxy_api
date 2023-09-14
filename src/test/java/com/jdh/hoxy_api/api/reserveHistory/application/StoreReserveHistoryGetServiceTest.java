package com.jdh.hoxy_api.api.reserveHistory.application;

import com.jdh.hoxy_api.api.reserveHistory.application.impl.StoreReserveHistoryGetServiceImpl;
import com.jdh.hoxy_api.api.reserveHistory.domain.entity.ReserveHistoryInfo;
import com.jdh.hoxy_api.api.reserveHistory.domain.entity.StoreReserveHistory;
import com.jdh.hoxy_api.api.reserveHistory.domain.entity.StoreReserveHistoryInfo;
import com.jdh.hoxy_api.api.reserveHistory.domain.entity.StoreReserveHistoryPK;
import com.jdh.hoxy_api.api.reserveHistory.domain.repository.StoreReserveHistoryRepository;
import com.jdh.hoxy_api.api.reserveHistory.dto.response.StoreReserveHistoryGetResponseDTO;
import com.jdh.hoxy_api.api.reserveHistory.enums.ReserveState;
import com.jdh.hoxy_api.api.reserveHistory.exception.StoreReserveHistoryException;
import com.jdh.hoxy_api.api.reserveHistory.exception.enums.StoreReserveHistoryErrorResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StoreReserveHistoryGetServiceTest {

    @InjectMocks
    StoreReserveHistoryGetServiceImpl target;

    @Mock
    StoreReserveHistoryRepository storeReserveHistoryRepository;

    @Test
    public void StoreReserveHistoryGetService_Not_Null() {
        assertThat(target).isNotNull();
    }

    @Test
    @DisplayName("StoreReserveHistoryGetService regDtStr 파라미터의 format이 일치하지 않는 경우 조회 실패 테스트")
    public void StoreReserveHistoryGetService_regDtStr_format_불일치_실패_테스트() {
        // given

        // when
        final StoreReserveHistoryException result = assertThrows(StoreReserveHistoryException.class, () -> target.getStoreReserveHistoryList(1, "20230914"));

        // then
        assertThat(result.getStoreReserveHistoryErrorResult()).isEqualTo(StoreReserveHistoryErrorResult.INCORRECT_SEARCH_FORMAT_REG_DT);
    }

    @Test
    @DisplayName("StoreReserveHistoryGetService 조회 성공 테스트")
    public void StoreReserveHistoryGetService_성공_테스트() throws Exception {
        // given
        final List<StoreReserveHistory> list = getTestHistoryList();
        String regDtStr = "2023-09-14";
        when(storeReserveHistoryRepository.getStoreReserveHistoryList(1, LocalDate.parse(regDtStr, DateTimeFormatter.ISO_DATE))).thenReturn(list);

        // when
        final List<StoreReserveHistoryGetResponseDTO> result = target.getStoreReserveHistoryList(1, regDtStr);

        // then
        assertThat(result).isNotNull();
        assertThat(result.get(0).getName()).isEqualTo("테스트");
        assertThat(result.get(0).getPhone()).isEqualTo("01012341234");
        assertThat(result.get(0).getReserveNum()).isEqualTo(2);
        assertThat(result.get(0).getReserveState()).isEqualTo(ReserveState.ACCEPT);
        assertThat(result.get(1).getName()).isEqualTo("테스트2");
        assertThat(result.get(1).getPhone()).isEqualTo("01056785678");
        assertThat(result.get(1).getReserveNum()).isEqualTo(4);
        assertThat(result.get(1).getReserveState()).isEqualTo(ReserveState.REJECT);
    }

    private List<StoreReserveHistory> getTestHistoryList() {
        List<StoreReserveHistory> returnList = new ArrayList<>();

        final StoreReserveHistoryPK pk1 = StoreReserveHistoryPK.builder()
                .idx(1)
                .storeIdx(1)
                .regDt(LocalDate.parse("2023-09-14", DateTimeFormatter.ISO_DATE))
                .build();
        final ReserveHistoryInfo historyInfo1 = ReserveHistoryInfo.builder()
                .name("테스트")
                .phone("01012341234")
                .reserveNum(2)
                .build();
        final StoreReserveHistoryInfo info1 = StoreReserveHistoryInfo.builder()
                .reserveState(ReserveState.ACCEPT)
                .build();
        final StoreReserveHistory value1 = StoreReserveHistory.builder()
                .storeReserveHistoryPK(pk1)
                .reserveHistoryInfo(historyInfo1)
                .storeReserveHistoryInfo(info1)
                .build();

        final StoreReserveHistoryPK pk2 = StoreReserveHistoryPK.builder()
                .idx(2)
                .storeIdx(1)
                .regDt(LocalDate.parse("2023-09-14", DateTimeFormatter.ISO_DATE))
                .build();
        final ReserveHistoryInfo historyInfo2 = ReserveHistoryInfo.builder()
                .name("테스트2")
                .phone("01056785678")
                .reserveNum(4)
                .build();
        final StoreReserveHistoryInfo info2 = StoreReserveHistoryInfo.builder()
                .reserveState(ReserveState.REJECT)
                .build();
        final StoreReserveHistory value2 = StoreReserveHistory.builder()
                .storeReserveHistoryPK(pk2)
                .reserveHistoryInfo(historyInfo2)
                .storeReserveHistoryInfo(info2)
                .build();

        returnList.add(value1);
        returnList.add(value2);

        return returnList;
    }

}
