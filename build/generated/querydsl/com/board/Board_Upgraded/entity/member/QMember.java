package com.board.Board_Upgraded.entity.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1103857893L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final com.board.Board_Upgraded.entity.base.QBaseEntity _super = new com.board.Board_Upgraded.entity.base.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final com.board.Board_Upgraded.domain.member.QMemberInfo memberInfo;

    public final com.board.Board_Upgraded.domain.member.QMemberPosts memberPosts;

    public final com.board.Board_Upgraded.domain.member.QPassword password;

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final com.board.Board_Upgraded.domain.member.QUsername username;

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberInfo = inits.isInitialized("memberInfo") ? new com.board.Board_Upgraded.domain.member.QMemberInfo(forProperty("memberInfo")) : null;
        this.memberPosts = inits.isInitialized("memberPosts") ? new com.board.Board_Upgraded.domain.member.QMemberPosts(forProperty("memberPosts")) : null;
        this.password = inits.isInitialized("password") ? new com.board.Board_Upgraded.domain.member.QPassword(forProperty("password")) : null;
        this.username = inits.isInitialized("username") ? new com.board.Board_Upgraded.domain.member.QUsername(forProperty("username")) : null;
    }

}

