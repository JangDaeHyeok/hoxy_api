package com.jdh.hoxy_api.api.store.dto.response;

import com.jdh.hoxy_api.api.store.domain.entity.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class StoreGetResponseDTO {

    private final String name;

    public static StoreGetResponseDTO of(Store store) {
        return StoreGetResponseDTO.builder()
                .name(store.getName())
                .build();
    }

}
