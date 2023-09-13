package com.jdh.hoxy_api.api.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDelYnEntity is a Querydsl query type for DelYnEntity
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDelYnEntity extends BeanPath<DelYnEntity> {

    private static final long serialVersionUID = -546430487L;

    public static final QDelYnEntity delYnEntity = new QDelYnEntity("delYnEntity");

    public final EnumPath<com.jdh.hoxy_api.api.common.enums.YorN> delYn = createEnum("delYn", com.jdh.hoxy_api.api.common.enums.YorN.class);

    public QDelYnEntity(String variable) {
        super(DelYnEntity.class, forVariable(variable));
    }

    public QDelYnEntity(Path<? extends DelYnEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDelYnEntity(PathMetadata metadata) {
        super(DelYnEntity.class, metadata);
    }

}

