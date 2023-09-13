package com.jdh.hoxy_api.api.store.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStore is a Querydsl query type for Store
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStore extends EntityPathBase<Store> {

    private static final long serialVersionUID = 1823767937L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStore store = new QStore("store");

    public final com.jdh.hoxy_api.api.common.entity.QRegModDtEntity _super = new com.jdh.hoxy_api.api.common.entity.QRegModDtEntity(this);

    public final com.jdh.hoxy_api.api.common.entity.QDelYnEntity delYn;

    public final NumberPath<Integer> idx = createNumber("idx", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDt = _super.modDt;

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public final QStoreAdmin storeAdmin;

    public final ListPath<com.jdh.hoxy_api.api.reserve.domain.entity.StoreReserve, com.jdh.hoxy_api.api.reserve.domain.entity.QStoreReserve> storeReserves = this.<com.jdh.hoxy_api.api.reserve.domain.entity.StoreReserve, com.jdh.hoxy_api.api.reserve.domain.entity.QStoreReserve>createList("storeReserves", com.jdh.hoxy_api.api.reserve.domain.entity.StoreReserve.class, com.jdh.hoxy_api.api.reserve.domain.entity.QStoreReserve.class, PathInits.DIRECT2);

    public QStore(String variable) {
        this(Store.class, forVariable(variable), INITS);
    }

    public QStore(Path<? extends Store> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStore(PathMetadata metadata, PathInits inits) {
        this(Store.class, metadata, inits);
    }

    public QStore(Class<? extends Store> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.delYn = inits.isInitialized("delYn") ? new com.jdh.hoxy_api.api.common.entity.QDelYnEntity(forProperty("delYn")) : null;
        this.storeAdmin = inits.isInitialized("storeAdmin") ? new QStoreAdmin(forProperty("storeAdmin")) : null;
    }

}

