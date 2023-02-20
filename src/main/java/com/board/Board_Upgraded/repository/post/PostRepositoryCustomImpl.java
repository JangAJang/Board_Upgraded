package com.board.Board_Upgraded.repository.post;

import com.board.Board_Upgraded.dto.post.PostResponseDto;
import com.board.Board_Upgraded.dto.post.SearchPostRequestDto;
import com.board.Board_Upgraded.entity.post.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PostResponseDto> searchPost(SearchPostRequestDto searchPostRequestDto, Pageable pageable) {
        queryFactory.selectFrom(QPost.post)
                .where()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return null;
    }
}
