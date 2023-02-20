package com.board.Board_Upgraded.dto.post;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.board.Board_Upgraded.dto.post.QPostResponseDto is a Querydsl Projection type for PostResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPostResponseDto extends ConstructorExpression<PostResponseDto> {

    private static final long serialVersionUID = -1392095817L;

    public QPostResponseDto(com.querydsl.core.types.Expression<String> writer, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<java.time.LocalDateTime> lastModifiedDate) {
        super(PostResponseDto.class, new Class<?>[]{String.class, String.class, String.class, java.time.LocalDateTime.class}, writer, title, content, lastModifiedDate);
    }

}

