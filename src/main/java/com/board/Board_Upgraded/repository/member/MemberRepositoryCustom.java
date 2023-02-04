package com.board.Board_Upgraded.repository.member;

import com.board.Board_Upgraded.dto.member.SearchMemberDto;
import com.board.Board_Upgraded.entity.member.Member;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> search(SearchMemberDto searchMemberDto);
}
