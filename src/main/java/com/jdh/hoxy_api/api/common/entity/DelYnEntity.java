package com.jdh.hoxy_api.api.common.entity;

import com.jdh.hoxy_api.api.common.enums.YorN;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Embeddable
public class DelYnEntity {

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    @ColumnDefault("0")
    private YorN delYn;

}
