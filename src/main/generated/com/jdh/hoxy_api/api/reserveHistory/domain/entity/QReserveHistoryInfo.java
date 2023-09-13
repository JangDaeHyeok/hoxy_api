package com.jdh.hoxy_api.api.reserveHistory.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReserveHistoryInfo is a Querydsl query type for ReserveHistoryInfo
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QReserveHistoryInfo extends BeanPath<ReserveHistoryInfo> {

    private static final long serialVersionUID = -435728629L;

    public static final QReserveHistoryInfo reserveHistoryInfo = new QReserveHistoryInfo("reserveHistoryInfo");

    public final StringPath name = createString("name");

    public final StringPath phone = createString("phone");

    public final NumberPath<Integer> reserveNum = createNumber("reserveNum", Integer.class);

    public QReserveHistoryInfo(String variable) {
        super(ReserveHistoryInfo.class, forVariable(variable));
    }

    public QReserveHistoryInfo(Path<? extends ReserveHistoryInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReserveHistoryInfo(PathMetadata metadata) {
        super(ReserveHistoryInfo.class, metadata);
    }

}

