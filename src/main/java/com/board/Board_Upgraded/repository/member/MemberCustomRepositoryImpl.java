package com.board.Board_Upgraded.repository.member;

import com.board.Board_Upgraded.dto.member.QSearchMemberDto;
import com.board.Board_Upgraded.dto.member.SearchMemberDto;
import com.board.Board_Upgraded.exception.member.NeedToAddSearchConditionException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import static com.board.Board_Upgraded.entity.member.QMember.*;
import static com.board.Board_Upgraded.repository.member.SearchType.*;

public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory query;

    public MemberCustomRepositoryImpl(EntityManager em){
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<SearchMemberDto> search(SearchMemberDto searchMemberDto, Pageable pageable, SearchType searchType) {
        if(searchMemberDto.getUsername() == null && searchMemberDto.getNickname() == null && searchMemberDto.getEmail() == null)
            throw new NeedToAddSearchConditionException();
        QueryResults<SearchMemberDto> result =  query
                .select(new QSearchMemberDto(member.username, member.nickname, member.email))
                .from(member)
                .where(searchCondition(searchMemberDto, searchType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<SearchMemberDto>(result.getResults(), pageable, result.getTotal());
    }

    private BooleanBuilder searchCondition(SearchMemberDto searchMemberDto, SearchType searchType){
        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(searchMemberDto.getUsername())){
            builder.or(queryUsername(searchMemberDto.getUsername(), searchType));
        }
        if(StringUtils.hasText(searchMemberDto.getNickname()))
            builder.or(queryNickname(searchMemberDto.getNickname(), searchType));
        if(StringUtils.hasText(searchMemberDto.getEmail()))
            builder.or(queryEmail(searchMemberDto.getEmail(), searchType));
        return builder;
    }

    private Predicate queryUsername(String username, SearchType searchType){
        if(searchType.equals(EXACT))
            return member.username.eq(username);
        return member.username.contains(username);
    }

    private Predicate queryNickname(String nickname, SearchType searchType){
        if(searchType.equals(EXACT))
            return member.nickname.eq(nickname);
        return member.nickname.contains(nickname);
    }

    private Predicate queryEmail(String email, SearchType searchType){
        if(searchType.equals(EXACT))
            return member.email.eq(email);
        return member.email.contains(email);
    }

//    private BooleanExpression usernameEq(String username, SearchType searchType){
//        if(username == null || username.isEmpty() || username.isBlank()) return null;
//        if(searchType.equals(CONTAINS)) return member.username.contains(username);
//        return member.username.eq(username);
//    }
//
//    private BooleanExpression nicknameEq(String nickname, SearchType searchType){
//        if(nickname == null || nickname.isEmpty() || nickname.isBlank()) return null;
//        if(searchType.equals(CONTAINS)) return member.nickname.contains(nickname);
//        return member.nickname.eq(nickname);
//    }
//
//    private BooleanExpression emailEq(String email, SearchType searchType){
//        if(email == null || email.isEmpty() || email.isBlank()) return null;
//        if(searchType.equals(CONTAINS)) return member.email.contains(email);
//        return member.email.eq(email);
//    }
}
