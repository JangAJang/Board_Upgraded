package com.board.Board_Upgraded.repository.member;


import com.board.Board_Upgraded.domain.member.Username;
import com.board.Board_Upgraded.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    Optional<Member> findByUsername(Username username);

    Optional<Member> findByMemberInfo_Nickname(String nickname);

    Optional<Member> findByMemberInfo_Email(String email);
}
