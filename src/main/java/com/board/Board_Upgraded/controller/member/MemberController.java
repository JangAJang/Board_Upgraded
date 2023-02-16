package com.board.Board_Upgraded.controller.member;

import com.board.Board_Upgraded.dto.member.SearchMemberDto;
import com.board.Board_Upgraded.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/search")
    public Page<SearchMemberDto> search(@RequestBody @Valid SearchMemberDto searchMemberDto,
                                        @PageableDefault Pageable pageable){
        return memberService.search(searchMemberDto, pageable);
    }
}
