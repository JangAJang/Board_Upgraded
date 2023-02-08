package com.board.Board_Upgraded.repository.member;

import com.board.Board_Upgraded.dto.member.QSearchMemberDto;
import com.board.Board_Upgraded.dto.member.SearchMemberDto;
import com.board.Board_Upgraded.exception.member.NeedToAddSearchConditionException;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.board.Board_Upgraded.entity.member.QMember.*;

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
                .where(usernameEq(searchMemberDto.getUsername()),
                        nicknameEq(searchMemberDto.getNickname()),
                        emailEq(searchMemberDto.getEmail()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<SearchMemberDto>(result.getResults(), pageable, result.getTotal());
    }

    private BooleanExpression usernameEq(String username){
        if(username == null || username.isEmpty() || username.isBlank()) return null;
        return member.username.eq(username);
    }

    private BooleanExpression nicknameEq(String nickname){
        if(nickname == null || nickname.isEmpty() || nickname.isBlank()) return null;
        return member.nickname.eq(nickname);
    }

    private BooleanExpression emailEq(String email){
        if(email == null || email.isEmpty() || email.isBlank()) return null;
        return member.email.eq(email);
    }
}
