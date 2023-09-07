package com.jdh.hoxy_api.api.reserve.dto.request;

import com.jdh.hoxy_api.api.reserveHistory.enums.ReserveState;
import com.jdh.hoxy_api.common.annotations.enums.Enum;
import com.jdh.hoxy_api.common.annotations.groups.ValidationGroups;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreReserveStateChgRequestDTO {

    @NotNull(groups = ValidationGroups.NotNullGroup.class)
    @Enum(enumClass = ReserveState.class, groups = ValidationGroups.EnumGroup.class)
    private String reserveState;

}
