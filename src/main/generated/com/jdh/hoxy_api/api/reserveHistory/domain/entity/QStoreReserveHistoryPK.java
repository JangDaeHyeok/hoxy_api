package com.jdh.hoxy_api.api.reserveHistory.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStoreReserveHistoryPK is a Querydsl query type for StoreReserveHistoryPK
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QStoreReserveHistoryPK extends BeanPath<StoreReserveHistoryPK> {

    private static final long serialVersionUID = -203607281L;

    public static final QStoreReserveHistoryPK storeReserveHistoryPK = new QStoreReserveHistoryPK("storeReserveHistoryPK");

    public final NumberPath<Integer> idx = createNumber("idx", Integer.class);

    public final DatePath<java.time.LocalDate> regDt = createDate("regDt", java.time.LocalDate.class);

    public final NumberPath<Integer> storeIdx = createNumber("storeIdx", Integer.class);

    public QStoreReserveHistoryPK(String variable) {
        super(StoreReserveHistoryPK.class, forVariable(variable));
    }

    public QStoreReserveHistoryPK(Path<? extends StoreReserveHistoryPK> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStoreReserveHistoryPK(PathMetadata metadata) {
        super(StoreReserveHistoryPK.class, metadata);
    }

}

