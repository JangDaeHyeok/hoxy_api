package com.jdh.hoxy_api.api.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRegModDtEntity is a Querydsl query type for RegModDtEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QRegModDtEntity extends EntityPathBase<RegModDtEntity> {

    private static final long serialVersionUID = -1442158245L;

    public static final QRegModDtEntity regModDtEntity = new QRegModDtEntity("regModDtEntity");

    public final DateTimePath<java.time.LocalDateTime> modDt = createDateTime("modDt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public QRegModDtEntity(String variable) {
        super(RegModDtEntity.class, forVariable(variable));
    }

    public QRegModDtEntity(Path<? extends RegModDtEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRegModDtEntity(PathMetadata metadata) {
        super(RegModDtEntity.class, metadata);
    }

}

