package com.board.Board_Upgraded.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberInfo is a Querydsl query type for MemberInfo
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMemberInfo extends BeanPath<MemberInfo> {

    private static final long serialVersionUID = -2076564214L;

    public static final QMemberInfo memberInfo = new QMemberInfo("memberInfo");

    public final StringPath email = createString("email");

    public final StringPath nickname = createString("nickname");

    public QMemberInfo(String variable) {
        super(MemberInfo.class, forVariable(variable));
    }

    public QMemberInfo(Path<? extends MemberInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberInfo(PathMetadata metadata) {
        super(MemberInfo.class, metadata);
    }

}

