package com.board.Board_Upgraded.entity.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = 808879707L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final com.board.Board_Upgraded.entity.base.QBaseEntity _super = new com.board.Board_Upgraded.entity.base.QBaseEntity(this);

    public final com.board.Board_Upgraded.domain.post.QContent content;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final com.board.Board_Upgraded.entity.member.QMember member;

    public final com.board.Board_Upgraded.domain.post.QTitle title;

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.content = inits.isInitialized("content") ? new com.board.Board_Upgraded.domain.post.QContent(forProperty("content")) : null;
        this.member = inits.isInitialized("member") ? new com.board.Board_Upgraded.entity.member.QMember(forProperty("member")) : null;
        this.title = inits.isInitialized("title") ? new com.board.Board_Upgraded.domain.post.QTitle(forProperty("title")) : null;
    }

}

