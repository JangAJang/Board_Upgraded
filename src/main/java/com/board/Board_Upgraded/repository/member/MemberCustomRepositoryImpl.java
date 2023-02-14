package com.board.Board_Upgraded.repository.member;

import com.board.Board_Upgraded.dto.member.QSearchMemberDto;
import com.board.Board_Upgraded.dto.member.SearchMemberDto;
import com.board.Board_Upgraded.exception.member.NeedToAddSearchConditionException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import static com.board.Board_Upgraded.entity.member.QMember.*;
import static org.springframework.util.StringUtils.*;

public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory query;

    public MemberCustomRepositoryImpl(EntityManager em){
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<SearchMemberDto> search(SearchMemberDto searchMemberDto, Pageable pageable) {
        if(searchMemberDto.getUsername() == null && searchMemberDto.getNickname() == null && searchMemberDto.getEmail() == null)
            throw new NeedToAddSearchConditionException();
        QueryResults<SearchMemberDto> result =  query
                .select(new QSearchMemberDto(member.username, member.nickname, member.email))
                .from(member)
                .where(searchCondition(searchMemberDto))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<SearchMemberDto>(result.getResults(), pageable, result.getTotal());
    }

    private BooleanBuilder searchCondition(SearchMemberDto searchMemberDto){
        return containsSearch(searchMemberDto);
    }

    private BooleanBuilder containsSearch(SearchMemberDto searchMemberDto){
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(searchMemberDto.getUsername())) builder.or(member.username.contains(searchMemberDto.getUsername()));
        if(hasText(searchMemberDto.getNickname())) builder.or(member.nickname.contains(searchMemberDto.getNickname()));
        if(hasText(searchMemberDto.getEmail())) builder.or(member.email.contains(searchMemberDto.getEmail()));
        return builder;
    }
}
