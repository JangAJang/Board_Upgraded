package com.board.Board_Upgraded.controller.member;

import com.board.Board_Upgraded.dto.member.EditMemberRequestDto;
import com.board.Board_Upgraded.dto.member.SearchMemberDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.response.Response;
import com.board.Board_Upgraded.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/info")
    public Response getMemberInfo(@RequestParam("username")String username){
        return Response.success(memberService.getMemberInfo(username));
    }

    @PatchMapping("/edit")
    public Response edit(@RequestBody EditMemberRequestDto editMemberRequestDto){
        memberService.editMemberInfo(editMemberRequestDto, getUsingMember());
        return Response.success("수정을 성공했습니다.");
    }

    @DeleteMapping("/delete")
    public Response delete(){
        return Response.success(memberService.deleteMember(getUsingMember()));
    }

    @GetMapping("/welcome")
    public Response welcome(){
        return Response.success(getUsingMember().getUsername());
    }

    private Member getUsingMember(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberService.findMemberByUsername(username);
    }
}
