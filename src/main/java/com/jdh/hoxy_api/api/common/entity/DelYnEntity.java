package com.jdh.hoxy_api.api.common.entity;

import com.jdh.hoxy_api.api.common.enums.YorN;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Embeddable
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DelYnEntity {

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private YorN delYn;

    public static DelYnEntity getDelN() {
        return DelYnEntity.builder()
                .delYn(YorN.N)
                .build();
    }

    public static DelYnEntity getDelY() {
        return DelYnEntity.builder()
                .delYn(YorN.Y)
                .build();
    }

}
