package com.board.Board_Upgraded.repository.post;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.post.PostResponseDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.entity.post.Post;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.repository.member.SearchPostType;
import com.board.Board_Upgraded.service.auth.AuthService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.board.Board_Upgraded.repository.member.SearchPostType.*;

@SpringBootTest
@Transactional
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    private static final PageRequest pageRequest = PageRequest.of(0, 10);

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

    @Test
    @DisplayName("게시물 검색시에, 검색타입을 WRITER로 2를 검색하면 멤버의 username에 2가 포함된 모든 게시물을 조회해온다. ")
    public void searchTest() throws Exception{
        //given
        IntStream.range(1, 4).forEach(i ->
                authService.registerNewMember(RegisterRequestDto.builder()
                        .username("test" + i)
                        .nickname("test" + i)
                        .email("test" + i + "@test.com")
                        .password("test" + i)
                        .passwordCheck("test" + i).build()));
        for(int index = 10; index < 40; index++){
            Member member = memberRepository.findByUsername("test" + index/10)
                    .orElseThrow(MemberNotFoundException::new);
            postRepository.save(Post.builder()
                    .member(member)
                    .title("title" + index)
                    .content("content" + index).build());
        }
        //when
        String searchMember = "2";
        //then
        Assertions.assertThat(postRepository.searchPost(searchMember, WRITER, pageRequest)
                        .getContent().stream().map(PostResponseDto::getTitle).collect(Collectors.toList()))
                .containsExactly("title20", "title21", "title22",
                        "title23", "title24", "title25",
                        "title26", "title27", "title28",
                        "title29");
    }

    @Test
    @DisplayName("제목에 3을 검색했을 때, 13, 23, 30~37이 나온다. ")
    public void searchTest_Title() throws Exception{
        //given
        IntStream.range(1, 4).forEach(i ->
                authService.registerNewMember(RegisterRequestDto.builder()
                        .username("test" + i)
                        .nickname("test" + i)
                        .email("test" + i + "@test.com")
                        .password("test" + i)
                        .passwordCheck("test" + i).build()));
        for(int index = 10; index < 40; index++){
            Member member = memberRepository.findByUsername("test" + index/10)
                    .orElseThrow(MemberNotFoundException::new);
            postRepository.save(Post.builder()
                    .member(member)
                    .title("title" + index)
                    .content("content" + index).build());
        }
        //when
        String searchMember = "3";
        //then
        Assertions.assertThat(postRepository.searchPost(searchMember, TITLE, pageRequest)
                        .getContent().stream().map(PostResponseDto::getTitle).collect(Collectors.toList()))
                .containsExactly(
                        "title13",
                        "title23", "title30", "title31", "title32",
                        "title33", "title34", "title35",
                        "title36", "title37");
    }

    @Test
    @DisplayName("내용에 3을 검색했을 때, 13, 23, 30~37이 나온다. ")
    public void searchTest_Content() throws Exception{
        //given
        IntStream.range(1, 4).forEach(i ->
                authService.registerNewMember(RegisterRequestDto.builder()
                        .username("test" + i)
                        .nickname("test" + i)
                        .email("test" + i + "@test.com")
                        .password("test" + i)
                        .passwordCheck("test" + i).build()));
        for(int index = 10; index < 40; index++){
            Member member = memberRepository.findByUsername("test" + index/10)
                    .orElseThrow(MemberNotFoundException::new);
            postRepository.save(Post.builder()
                    .member(member)
                    .title("title" + index)
                    .content("content" + index).build());
        }
        //when
        String searchMember = "3";
        //then
        Assertions.assertThat(postRepository.searchPost(searchMember, CONTENT, pageRequest)
                        .getContent().stream().map(PostResponseDto::getTitle).collect(Collectors.toList()))
                .containsExactly(
                        "title13",
                        "title23", "title30", "title31", "title32",
                        "title33", "title34", "title35",
                        "title36", "title37");
    }
}
