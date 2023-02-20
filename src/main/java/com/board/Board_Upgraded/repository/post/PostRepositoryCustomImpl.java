package com.board.Board_Upgraded.repository.post;

import com.board.Board_Upgraded.dto.post.PostResponseDto;
import com.board.Board_Upgraded.entity.post.Post;
import com.board.Board_Upgraded.entity.post.QPost;
import com.board.Board_Upgraded.repository.member.SearchPostType;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Post> searchPost(String text, SearchPostType searchPostType, Pageable pageable) {
        QueryResults<Post> result = queryFactory.selectFrom(QPost.post)
                .where(makeConditionQuery(text, searchPostType))
                .orderBy(QPost.post.lastModifiedDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    private BooleanExpression makeConditionQuery(String text, SearchPostType searchPostType){
        if(searchPostType.equals(SearchPostType.TITLE))
            return QPost.post.title.contains(text);
        if(searchPostType.equals(SearchPostType.CONTENT))
            return QPost.post.content.contains(text);
        return QPost.post.title.contains(text).or(QPost.post.content.contains(text));
    }
}
