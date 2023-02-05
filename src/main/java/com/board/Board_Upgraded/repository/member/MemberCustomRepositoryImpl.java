package com.board.Board_Upgraded.repository.member;

import com.board.Board_Upgraded.dto.member.SearchMemberDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.entity.member.QMember;
import com.board.Board_Upgraded.exception.member.NeedToAddSearchConditionException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory query;

    public MemberCustomRepositoryImpl(EntityManager em){
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<Member> search(SearchMemberDto searchMemberDto) {
        return query.selectFrom(QMember.member)
                .where(QMember.member.username.eq(searchMemberDto.getUsername()),
                        QMember.member.nickname.eq(searchMemberDto.getNickname()),
                        QMember.member.email.eq(searchMemberDto.getEmail())).fetch();
    }



    //    @Override
//    public List<Member> search(SearchMemberDto searchMemberDto) {
//        StringBuilder query = new StringBuilder("select m from Member m ");
//        if(searchMemberDto.getUsername() == null && searchMemberDto.getNickname() == null && searchMemberDto.getEmail() == null)
//            throw new NeedToAddSearchConditionException();
//        if(searchMemberDto.getUsername() == null && searchMemberDto.getNickname() == null)
//            return em.createQuery("select m from Member m where m.email = :email", Member.class)
//                    .setParameter("email", searchMemberDto.getEmail()).getResultList();
//        if(searchMemberDto.getNickname() == null && searchMemberDto.getEmail() == null)
//            return em.createQuery("select m from Member m where m.username = :username", Member.class)
//                    .setParameter("username", searchMemberDto.getUsername()).getResultList();
//        if(searchMemberDto.getUsername() == null && searchMemberDto.getEmail() == null)
//            return em.createQuery("select m from Member m where m.nickname = :nickname", Member.class)
//                    .setParameter("nickname", searchMemberDto.getNickname()).getResultList();
//        if(searchMemberDto.getUsername() == null)
//            return em.createQuery("select m from Member m where m.nickname = :nickname and m.email = :email", Member.class)
//                    .setParameter("nickname", searchMemberDto.getNickname())
//                    .setParameter("email", searchMemberDto.getEmail())
//                    .getResultList();
//        if(searchMemberDto.getNickname() == null)
//            return em.createQuery("select m from Member m where m.username = :username and m.email = :email", Member.class)
//                    .setParameter("username", searchMemberDto.getUsername())
//                    .setParameter("email", searchMemberDto.getEmail())
//                    .getResultList();
//        if(searchMemberDto.getEmail() == null)
//            return em.createQuery("select m from Member m where m.username = :username and m.nickname = :nickname", Member.class)
//                    .setParameter("username", searchMemberDto.getUsername())
//                    .setParameter("nickname", searchMemberDto.getNickname())
//                    .getResultList();
//        return em.createQuery("select m from Member m where m.username = :username " +
//                        "and m.nickname = :nickname " +
//                        "and m.email = :email", Member.class)
//                .setParameter("username", searchMemberDto.getUsername())
//                .setParameter("nickname", searchMemberDto.getNickname())
//                .setParameter("email", searchMemberDto.getEmail())
//                .getResultList();
//    }


}
