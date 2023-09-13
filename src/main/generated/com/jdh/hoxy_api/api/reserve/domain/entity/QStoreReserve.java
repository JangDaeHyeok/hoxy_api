package com.jdh.hoxy_api.api.reserve.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStoreReserve is a Querydsl query type for StoreReserve
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStoreReserve extends EntityPathBase<StoreReserve> {

    private static final long serialVersionUID = 116015808L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStoreReserve storeReserve = new QStoreReserve("storeReserve");

    public final com.jdh.hoxy_api.api.common.entity.QRegModDtEntity _super = new com.jdh.hoxy_api.api.common.entity.QRegModDtEntity(this);

    public final com.jdh.hoxy_api.api.common.entity.QDelYnEntity delYn;

    public final NumberPath<Integer> idx = createNumber("idx", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDt = _super.modDt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public final QReserveInfo reserveInfo;

    public final com.jdh.hoxy_api.api.store.domain.entity.QStore store;

    public QStoreReserve(String variable) {
        this(StoreReserve.class, forVariable(variable), INITS);
    }

    public QStoreReserve(Path<? extends StoreReserve> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStoreReserve(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStoreReserve(PathMetadata metadata, PathInits inits) {
        this(StoreReserve.class, metadata, inits);
    }

    public QStoreReserve(Class<? extends StoreReserve> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.delYn = inits.isInitialized("delYn") ? new com.jdh.hoxy_api.api.common.entity.QDelYnEntity(forProperty("delYn")) : null;
        this.reserveInfo = inits.isInitialized("reserveInfo") ? new QReserveInfo(forProperty("reserveInfo")) : null;
        this.store = inits.isInitialized("store") ? new com.jdh.hoxy_api.api.store.domain.entity.QStore(forProperty("store"), inits.get("store")) : null;
    }

}

