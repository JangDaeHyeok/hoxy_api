package com.jdh.hoxy_api.api.store.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStoreAdmin is a Querydsl query type for StoreAdmin
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStoreAdmin extends EntityPathBase<StoreAdmin> {

    private static final long serialVersionUID = -1854546354L;

    public static final QStoreAdmin storeAdmin = new QStoreAdmin("storeAdmin");

    public final com.jdh.hoxy_api.api.common.entity.QRegModDtEntity _super = new com.jdh.hoxy_api.api.common.entity.QRegModDtEntity(this);

    public final StringPath id = createString("id");

    public final NumberPath<Integer> idx = createNumber("idx", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDt = _super.modDt;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public QStoreAdmin(String variable) {
        super(StoreAdmin.class, forVariable(variable));
    }

    public QStoreAdmin(Path<? extends StoreAdmin> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStoreAdmin(PathMetadata metadata) {
        super(StoreAdmin.class, metadata);
    }

}

