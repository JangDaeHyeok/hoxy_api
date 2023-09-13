package com.jdh.hoxy_api.api.reserveHistory.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStoreReserveHistory is a Querydsl query type for StoreReserveHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStoreReserveHistory extends EntityPathBase<StoreReserveHistory> {

    private static final long serialVersionUID = 1635540500L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStoreReserveHistory storeReserveHistory = new QStoreReserveHistory("storeReserveHistory");

    public final QReserveHistoryInfo reserveHistoryInfo;

    public final QStoreReserveHistoryInfo storeReserveHistoryInfo;

    public final QStoreReserveHistoryPK storeReserveHistoryPK;

    public QStoreReserveHistory(String variable) {
        this(StoreReserveHistory.class, forVariable(variable), INITS);
    }

    public QStoreReserveHistory(Path<? extends StoreReserveHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStoreReserveHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStoreReserveHistory(PathMetadata metadata, PathInits inits) {
        this(StoreReserveHistory.class, metadata, inits);
    }

    public QStoreReserveHistory(Class<? extends StoreReserveHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reserveHistoryInfo = inits.isInitialized("reserveHistoryInfo") ? new QReserveHistoryInfo(forProperty("reserveHistoryInfo")) : null;
        this.storeReserveHistoryInfo = inits.isInitialized("storeReserveHistoryInfo") ? new QStoreReserveHistoryInfo(forProperty("storeReserveHistoryInfo")) : null;
        this.storeReserveHistoryPK = inits.isInitialized("storeReserveHistoryPK") ? new QStoreReserveHistoryPK(forProperty("storeReserveHistoryPK")) : null;
    }

}

