package com.jdh.hoxy_api.api.reserveHistory.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStoreReserveHistoryInfo is a Querydsl query type for StoreReserveHistoryInfo
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QStoreReserveHistoryInfo extends BeanPath<StoreReserveHistoryInfo> {

    private static final long serialVersionUID = 1901726946L;

    public static final QStoreReserveHistoryInfo storeReserveHistoryInfo = new QStoreReserveHistoryInfo("storeReserveHistoryInfo");

    public final DateTimePath<java.time.LocalDateTime> reserveProcDt = createDateTime("reserveProcDt", java.time.LocalDateTime.class);

    public final EnumPath<com.jdh.hoxy_api.api.reserveHistory.enums.ReserveState> reserveState = createEnum("reserveState", com.jdh.hoxy_api.api.reserveHistory.enums.ReserveState.class);

    public QStoreReserveHistoryInfo(String variable) {
        super(StoreReserveHistoryInfo.class, forVariable(variable));
    }

    public QStoreReserveHistoryInfo(Path<? extends StoreReserveHistoryInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStoreReserveHistoryInfo(PathMetadata metadata) {
        super(StoreReserveHistoryInfo.class, metadata);
    }

}

