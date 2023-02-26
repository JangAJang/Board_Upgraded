package com.board.Board_Upgraded.controller.post;

import com.board.Board_Upgraded.dto.post.PostResponseDto;
import com.board.Board_Upgraded.dto.post.WritePostRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.response.Response;
import com.board.Board_Upgraded.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private Member getCurrentMember(){
        return memberRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(MemberNotFoundException::new);
    }
}
