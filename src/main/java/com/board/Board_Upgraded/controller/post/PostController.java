package com.board.Board_Upgraded.controller.post;

import com.board.Board_Upgraded.dto.post.EditPostRequestDto;
import com.board.Board_Upgraded.dto.post.PostResponseDto;
import com.board.Board_Upgraded.dto.post.SearchPostRequestDto;
import com.board.Board_Upgraded.dto.post.WritePostRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.repository.member.SearchPostType;
import com.board.Board_Upgraded.response.Response;
import com.board.Board_Upgraded.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberRepository memberRepository;

    @PostMapping("/write")
    public Response write(@RequestBody @Valid WritePostRequestDto writePostRequestDto){
        PostResponseDto postResponseDto = postService.write(writePostRequestDto, getCurrentMember());
        return Response.success(postResponseDto);
    }

    @PatchMapping("/edit")
    public Response edit(@RequestBody EditPostRequestDto editPostRequestDto, @RequestParam("id") Long id){
        PostResponseDto postResponseDto = postService.edit(editPostRequestDto, getCurrentMember(), id);
        return Response.success(postResponseDto);
    }

    @DeleteMapping("/delete")
    public Response delete(@RequestParam("id") Long post){
        postService.delete(getCurrentMember(), post);
        return Response.success("삭제 완료");
    }

    @GetMapping("/search")
    public Response search(@RequestBody @Valid SearchPostRequestDto searchPostRequestDto, @PageableDefault Pageable pageable, @RequestParam("type") SearchPostType searchPostType){
        return Response.success(postService.search(searchPostRequestDto, pageable, searchPostType));
    }

    @GetMapping("/of")
    public Response getPostOf(@RequestParam Long member, @PageableDefault Pageable pageable){
        return Response.success(postService.getMembersPage(member, pageable));
    }

    private Member getCurrentMember(){
        return memberRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(MemberNotFoundException::new);
    }
}
