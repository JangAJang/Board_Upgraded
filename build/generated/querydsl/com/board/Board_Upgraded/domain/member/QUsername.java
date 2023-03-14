package com.board.Board_Upgraded.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsername is a Querydsl query type for Username
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QUsername extends BeanPath<Username> {

    private static final long serialVersionUID = -208377864L;

    public static final QUsername username1 = new QUsername("username1");

    public final StringPath username = createString("username");

    public QUsername(String variable) {
        super(Username.class, forVariable(variable));
    }

    public QUsername(Path<? extends Username> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsername(PathMetadata metadata) {
        super(Username.class, metadata);
    }

}

