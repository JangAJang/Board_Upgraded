package com.board.Board_Upgraded.repository.member;

import com.board.Board_Upgraded.dto.member.QSearchMemberDto;
import com.board.Board_Upgraded.dto.member.SearchMemberDto;
import com.board.Board_Upgraded.exception.member.NeedToAddSearchConditionException;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.board.Board_Upgraded.entity.member.QMember.*;

public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory query;

    public MemberCustomRepositoryImpl(EntityManager em){
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<SearchMemberDto> search(SearchMemberDto searchMemberDto) {
        if(searchMemberDto.getUsername() == null && searchMemberDto.getNickname() == null && searchMemberDto.getEmail() == null)
            throw new NeedToAddSearchConditionException();
        return query
                .select(new QSearchMemberDto(member.username, member.nickname, member.email))
                .from(member)
                .where(usernameEq(searchMemberDto.getUsername()),
                        nicknameEq(searchMemberDto.getNickname()),
                        emailEq(searchMemberDto.getEmail()))
                .fetch();
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
