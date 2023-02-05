package com.board.Board_Upgraded.repository.member;

import com.board.Board_Upgraded.dto.member.SearchMemberDto;

import java.util.List;

public interface MemberCustomRepository {

    List<SearchMemberDto> search(SearchMemberDto searchMemberDto);
}
