package com.board.Board_Upgraded.service.post;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.post.WritePostRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.repository.post.PostRepository;
import com.board.Board_Upgraded.service.auth.AuthService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void createWriter(){
        authService.registerNewMember(RegisterRequestDto.builder()
                .username("test")
                .nickname("test")
                .email("test@test.com")
                .password("test")
                .passwordCheck("test")
                .build());
    }

    @Test
    @DisplayName("게시물의 제목과 내용이 있으면 글을 작성해준다. ")
    public void writeSuccess() throws Exception{
        //given
        Member member = memberRepository.findByUsername("test").orElseThrow(MemberNotFoundException::new);
        WritePostRequestDto writePostRequestDto = WritePostRequestDto.builder()
                .title("취업")
                .content("가즈아").build();
        //when
        postService.write(writePostRequestDto, member);
        //then
        Assertions.assertThat(postRepository.count()).isEqualTo(1L);
    }
}
