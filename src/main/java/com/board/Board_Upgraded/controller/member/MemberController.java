package com.board.Board_Upgraded.controller.member;

import com.board.Board_Upgraded.dto.member.ChangeNicknameRequestDto;
import com.board.Board_Upgraded.dto.member.SearchMemberDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.response.Response;
import com.board.Board_Upgraded.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/search")
    public Response search(@RequestBody @Valid SearchMemberDto searchMemberDto,
                           @PageableDefault Pageable pageable){
        Page<SearchMemberDto> searchResult = memberService.search(searchMemberDto, pageable);
        return Response.success(searchResult);
    }

    @PutMapping("/update/nickname")
    public Response updateNickname(@RequestBody @Valid ChangeNicknameRequestDto changeNicknameRequestDto){
        Member member = memberService.findMemberByUsername(getCurrentMembersUsername());
        memberService.changeMemberNickname(changeNicknameRequestDto, member);
        return Response.success("닉네임 변경 성공");
    }

    private String getCurrentMembersUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
