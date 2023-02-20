package com.board.Board_Upgraded.repository.post;

import com.board.Board_Upgraded.dto.post.PostResponseDto;
import com.board.Board_Upgraded.dto.post.QPostResponseDto;
import com.board.Board_Upgraded.repository.member.SearchPostType;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.board.Board_Upgraded.entity.member.QMember.*;
import static com.board.Board_Upgraded.entity.post.QPost.*;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PostResponseDto> searchPost(String text, SearchPostType searchPostType, Pageable pageable) {
        QueryResults<PostResponseDto> result = queryFactory
                .select(new QPostResponseDto(member.nickname.as("writer"),
                        post.title, post.content, post.lastModifiedDate))
                .from(post)
                .leftJoin(post.member, member)
                .where(makeConditionQuery(text, searchPostType))
                .orderBy(post.lastModifiedDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    private BooleanExpression makeConditionQuery(String text, SearchPostType searchPostType){
        if(searchPostType.equals(SearchPostType.TITLE))
            return post.title.contains(text);
        if(searchPostType.equals(SearchPostType.CONTENT))
            return post.content.contains(text);
        return post.title.contains(text).or(post.content.contains(text));
    }
}
