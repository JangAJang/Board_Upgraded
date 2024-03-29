package com.board.Board_Upgraded.repository.post;

import com.board.Board_Upgraded.dto.post.PostResponseDto;
import com.board.Board_Upgraded.dto.post.QPostResponseDto;
import com.board.Board_Upgraded.dto.post.SearchPostRequestDto;
import com.board.Board_Upgraded.repository.member.SearchPostType;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static com.board.Board_Upgraded.entity.member.QMember.*;
import static com.board.Board_Upgraded.entity.post.QPost.*;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PostResponseDto> searchPost(SearchPostRequestDto searchPostRequestDto, SearchPostType searchPostType, Pageable pageable) {
        QueryResults<PostResponseDto> result = queryFactory
                .select(new QPostResponseDto(post.id, member.memberInfo.nickname.as("writer"),
                        post.title.title, post.content.content, post.lastModifiedDate))
                .from(post)
                .leftJoin(post.member, member)
                .where(makeConditionQuery(searchPostRequestDto.getText(), searchPostType))
                .orderBy(post.lastModifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Page<PostResponseDto> getMembersPost(Long id, Pageable pageable) {
        QueryResults<PostResponseDto> result = queryFactory
                .select(new QPostResponseDto(post.id, member.memberInfo.nickname.as("writer"),
                        post.title.title, post.content.content, post.lastModifiedDate))
                .from(post)
                .leftJoin(post.member, member)
                .where(member.id.eq(id))
                .orderBy(post.lastModifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    private BooleanExpression makeConditionQuery(String text, SearchPostType searchPostType){
        if(searchPostType.equals(SearchPostType.WRITER) || searchPostType.equals(SearchPostType.WRITER_AND_TITLE))
            return makeConditionQueryWithMember(text, searchPostType);
        return makeConditionQueryWithoutMember(text, searchPostType);
    }

    private BooleanExpression makeConditionQueryWithMember(String text, SearchPostType searchPostType){
        if(searchPostType.equals(SearchPostType.WRITER))
            return member.memberInfo.nickname.contains(text);
        return member.memberInfo.nickname.contains(text).or(post.title.title.contains(text));
    }

    private BooleanExpression makeConditionQueryWithoutMember(String text, SearchPostType searchPostType){
        if(searchPostType.equals(SearchPostType.TITLE))
            return post.title.title.contains(text);
        if(searchPostType.equals(SearchPostType.CONTENT))
            return post.content.content.contains(text);
        return post.title.title.contains(text).or(post.content.content.contains(text));
    }
}
