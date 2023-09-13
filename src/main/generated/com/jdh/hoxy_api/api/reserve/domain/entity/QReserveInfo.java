package com.jdh.hoxy_api.api.reserve.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReserveInfo is a Querydsl query type for ReserveInfo
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QReserveInfo extends BeanPath<ReserveInfo> {

    private static final long serialVersionUID = -933025595L;

    public static final QReserveInfo reserveInfo = new QReserveInfo("reserveInfo");

    public final StringPath name = createString("name");

    public final StringPath phone = createString("phone");

    public final NumberPath<Integer> reserveNum = createNumber("reserveNum", Integer.class);

    public QReserveInfo(String variable) {
        super(ReserveInfo.class, forVariable(variable));
    }

    public QReserveInfo(Path<? extends ReserveInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReserveInfo(PathMetadata metadata) {
        super(ReserveInfo.class, metadata);
    }

}

