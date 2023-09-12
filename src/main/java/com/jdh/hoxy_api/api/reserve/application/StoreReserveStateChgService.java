package com.jdh.hoxy_api.api.reserve.application;

import com.jdh.hoxy_api.api.reserveHistory.enums.ReserveState;

public interface StoreReserveStateChgService {

    void editStoreReserveState(final int idx, final ReserveState reserveState, final int storeIdx) throws Exception;

}
