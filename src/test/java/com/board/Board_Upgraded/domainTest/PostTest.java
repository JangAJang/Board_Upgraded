package com.board.Board_Upgraded.domainTest;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.entity.post.Post;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.service.auth.AuthService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class PostTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("제목, 내용, Member를 파라미터로 받아 게시글을 생성한다.")
    public void CreateTest() throws Exception{
        //given
        authService.registerNewMember(RegisterRequestDto.builder()
                .username("test")
                .nickname("test")
                .email("test@test.com")
                .password("test")
                .passwordCheck("test").build());
        Member member = memberRepository.findByUsername("test").orElseThrow(MemberNotFoundException::new);
        //when
        String title = "제목";
        String content = "내용";
        //then
        Post post = new Post(title, content, member);
        Assertions.assertThat(post.getTitle()).isEqualTo(title);
        Assertions.assertThat(post.getContent()).isEqualTo(content);
        Assertions.assertThat(post.getMember()).isEqualTo(member);
    }

    @Test
    @DisplayName("게시물을 수정하면, 인스턴스가 초기화되어 조회시에 바뀐 값으로 나온다. ")
    public void editTest() throws Exception{
        //given
        authService.registerNewMember(RegisterRequestDto.builder()
                .username("test")
                .nickname("test")
                .email("test@test.com")
                .password("test")
                .passwordCheck("test").build());
        Member member = memberRepository.findByUsername("test").orElseThrow(MemberNotFoundException::new);
        String title = "제목";
        String content = "내용";
        Post post = new Post(title, content, member);
        //when
        post.editPost("제목1", "내용1");
        //then
        Assertions.assertThat(post.getTitle()).isEqualTo("제목1");
        Assertions.assertThat(post.getContent()).isEqualTo("내용1");
    }
}
