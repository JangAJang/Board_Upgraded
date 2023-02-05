package com.board.Board_Upgraded.dto.member;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.board.Board_Upgraded.dto.member.QSearchMemberDto is a Querydsl Projection type for SearchMemberDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QSearchMemberDto extends ConstructorExpression<SearchMemberDto> {

    private static final long serialVersionUID = -1741614864L;

    public QSearchMemberDto(com.querydsl.core.types.Expression<String> username, com.querydsl.core.types.Expression<String> nickname, com.querydsl.core.types.Expression<String> email) {
        super(SearchMemberDto.class, new Class<?>[]{String.class, String.class, String.class}, username, nickname, email);
    }

}

