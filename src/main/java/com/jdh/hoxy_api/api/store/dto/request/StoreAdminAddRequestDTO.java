package com.jdh.hoxy_api.api.store.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreAdminAddRequestDTO {

    @NotNull
    private Integer storeIdx;

    @NotNull
    @NotBlank
    private String id;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String name;

    @Builder
    public StoreAdminAddRequestDTO(Integer storeIdx, String id, String password, String name) {
        this.storeIdx = storeIdx;
        this.id = id;
        this.password = password;
        this.name = name;
    }
}
