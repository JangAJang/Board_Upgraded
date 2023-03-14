package com.board.Board_Upgraded.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLogInValue is a Querydsl query type for LogInValue
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QLogInValue extends BeanPath<LogInValue> {

    private static final long serialVersionUID = -1589185382L;

    public static final QLogInValue logInValue = new QLogInValue("logInValue");

    public final StringPath password = createString("password");

    public final StringPath username = createString("username");

    public QLogInValue(String variable) {
        super(LogInValue.class, forVariable(variable));
    }

    public QLogInValue(Path<? extends LogInValue> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLogInValue(PathMetadata metadata) {
        super(LogInValue.class, metadata);
    }

}

