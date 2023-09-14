package com.jdh.hoxy_api.api.reserveHistory.application.impl;

import com.jdh.hoxy_api.api.reserveHistory.application.StoreReserveHistoryGetService;
import com.jdh.hoxy_api.api.reserveHistory.domain.repository.StoreReserveHistoryRepository;
import com.jdh.hoxy_api.api.reserveHistory.dto.response.StoreReserveHistoryGetResponseDTO;
import com.jdh.hoxy_api.api.reserveHistory.exception.StoreReserveHistoryException;
import com.jdh.hoxy_api.api.reserveHistory.exception.enums.StoreReserveHistoryErrorResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreReserveHistoryGetServiceImpl implements StoreReserveHistoryGetService {

    private final StoreReserveHistoryRepository storeReserveHistoryRepository;

    @Override
    public List<StoreReserveHistoryGetResponseDTO> getStoreReserveHistoryList(final int storeIdx, final String regDtStr) throws Exception {
        // String regDtStr to LocalDate
        LocalDate regDt = null;
        try {
            regDt = LocalDate.parse(regDtStr, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            // regDtStr format이 "yyyy-MM-dd" 가 아닌 경우
            throw new StoreReserveHistoryException(StoreReserveHistoryErrorResult.INCORRECT_SEARCH_FORMAT_REG_DT);
        }


        var findList = storeReserveHistoryRepository.getStoreReserveHistoryList(storeIdx, regDt);

        return findList.stream()
                .map(StoreReserveHistoryGetResponseDTO::of)
                .toList();
    }
}
