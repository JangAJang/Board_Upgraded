package com.board.Board_Upgraded.repository.post;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.entity.post.Post;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
import com.board.Board_Upgraded.repository.member.MemberRepository;
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
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void makeMember(){
        authService.registerNewMember(RegisterRequestDto.builder()
                .username("test")
                .nickname("test")
                .email("test@test.com")
                .password("test")
                .passwordCheck("test").build());
    }

    @Test
    @DisplayName("게시물을 생성하면 리포지토리 사이즈가 증가한다. ")
    public void saveTest() throws Exception{
        //given
        Member member = memberRepository.findByUsername("test").orElseThrow(MemberNotFoundException::new);
        //when
        postRepository.save(Post.builder()
                .title("title")
                .content("content")
                .member(member).build());
        //then
        Assertions.assertThat(postRepository.count()).isEqualTo(1L);
    }

    @Test
    @DisplayName("게시물을 삭제하면 리포지토리에서 사이즈가 감소한다.")
    public void deleteTest() throws Exception{
        //given
        Member member = memberRepository.findByUsername("test").orElseThrow(MemberNotFoundException::new);
        //when
        Post post = Post.builder()
                .title("title")
                .content("content")
                .member(member).build();
        postRepository.save(post);
        long countBefore = postRepository.count();
        postRepository.delete(post);
        //then
        Assertions.assertThat(postRepository.count()).isNotEqualTo(countBefore);
    }
}
