package com.board.Board_Upgraded.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberPosts is a Querydsl query type for MemberPosts
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMemberPosts extends BeanPath<MemberPosts> {

    private static final long serialVersionUID = 57526007L;

    public static final QMemberPosts memberPosts = new QMemberPosts("memberPosts");

    public final ListPath<com.board.Board_Upgraded.entity.post.Post, com.board.Board_Upgraded.entity.post.QPost> posts = this.<com.board.Board_Upgraded.entity.post.Post, com.board.Board_Upgraded.entity.post.QPost>createList("posts", com.board.Board_Upgraded.entity.post.Post.class, com.board.Board_Upgraded.entity.post.QPost.class, PathInits.DIRECT2);

    public QMemberPosts(String variable) {
        super(MemberPosts.class, forVariable(variable));
    }

    public QMemberPosts(Path<? extends MemberPosts> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberPosts(PathMetadata metadata) {
        super(MemberPosts.class, metadata);
    }

}

