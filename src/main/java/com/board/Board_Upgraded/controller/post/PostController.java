package com.board.Board_Upgraded.controller.post;

import com.board.Board_Upgraded.dto.post.EditPostRequestDto;
import com.board.Board_Upgraded.dto.post.PostResponseDto;
import com.board.Board_Upgraded.dto.post.WritePostRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.response.Response;
import com.board.Board_Upgraded.service.post.PostService;
import lombok.RequiredArgsConstructor;
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

    @PatchMapping("/")
    public Response edit(@RequestBody @Valid EditPostRequestDto editPostRequestDto, @RequestParam Long post){
        PostResponseDto postResponseDto = postService.edit(editPostRequestDto, getCurrentMember(), post);
        return Response.success(postResponseDto);
    }

    @DeleteMapping("/")
    public Response delete(@RequestParam Long post){
        postService.delete(getCurrentMember(), post);
        return Response.success("삭제 완료");
    }

    private Member getCurrentMember(){
        return memberRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(MemberNotFoundException::new);
    }
}
