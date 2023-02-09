package com.board.Board_Upgraded.repository.member;

import com.board.Board_Upgraded.dto.member.SearchMemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberCustomRepository {

    Page<SearchMemberDto> search(SearchMemberDto searchMemberDto, Pageable pageable, SearchType searchType);
}
