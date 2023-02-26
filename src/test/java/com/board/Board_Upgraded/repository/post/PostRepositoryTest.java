package com.board.Board_Upgraded.repository.post;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.post.PostResponseDto;
import com.board.Board_Upgraded.dto.post.SearchPostRequestDto;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.concurrent.TimeUnit;
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

    @Autowired
    private EntityManager em;

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
    @DisplayName("게시물 검색시에, 검색타입을 WRITER로 2를 검색하면 멤버의 username에 2가 포함된 모든 게시물을 역순으로 페이징처리해 조회해온다. ")
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
        SearchPostRequestDto searchMember = new SearchPostRequestDto("2");
        //then
        Assertions.assertThat(postRepository.searchPost(searchMember, WRITER, pageRequest)
                        .getContent().stream().map(PostResponseDto::getTitle).collect(Collectors.toList()))
                .containsExactly("title29", "title28", "title27",
                        "title26", "title25", "title24",
                        "title23", "title22", "title21",
                        "title20");
    }

    @Test
    @DisplayName("제목에 3을 검색했을 때, 최근에 만들어진 39~30순으로 한 페이지에 나온다. ")
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
        SearchPostRequestDto searchMember = new SearchPostRequestDto("3");
        //then
        Assertions.assertThat(postRepository.searchPost(searchMember, TITLE, pageRequest)
                        .getContent().stream().map(PostResponseDto::getTitle).collect(Collectors.toList()))
                .containsExactly(
                        "title39", "title38", "title37",
                        "title36", "title35", "title34",
                        "title33", "title32", "title31",
                        "title30");
    }

    @Test
    @DisplayName("내용에 3을 검색했을 때, 최근에 만들어진 39~30순으로 한 페이지에 나온다. ")
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
        SearchPostRequestDto searchMember = new SearchPostRequestDto("3");
        //then
        Assertions.assertThat(postRepository.searchPost(searchMember, CONTENT, pageRequest)
                        .getContent().stream().map(PostResponseDto::getTitle).collect(Collectors.toList()))
                .containsExactly(
                        "title39", "title38", "title37",
                        "title36", "title35", "title34",
                        "title33", "title32", "title31",
                        "title30");
    }

    @Test
    @DisplayName("제목과 내용에 3을 검색했을 때, 최근에 만들어진 39~30순으로 한 페이지에 나온다. ")
    public void searchTest_TITLE_AND_CONTENT() throws Exception{
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
        SearchPostRequestDto searchMember = new SearchPostRequestDto("3");
        //then
        Assertions.assertThat(postRepository.searchPost(searchMember, TITLE_AND_CONTENT, pageRequest)
                        .getContent().stream().map(PostResponseDto::getTitle).collect(Collectors.toList()))
                .containsExactly(
                        "title39", "title38", "title37",
                        "title36", "title35", "title34",
                        "title33", "title32", "title31",
                        "title30");
        Assertions.assertThat(postRepository.searchPost(searchMember, TITLE_AND_CONTENT, pageRequest).getTotalElements()).isEqualTo(12L);
    }

    @Test
    @DisplayName("글쓴이 이름과 제목에 3을 검색했을 때, 최근에 만들어진 39~30순으로 한 페이지에 나온다. ")
    public void searchTest_WRITER_AND_TITLE() throws Exception{
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
        SearchPostRequestDto searchMember = new SearchPostRequestDto("3");
        //then
        Assertions.assertThat(postRepository.searchPost(searchMember, WRITER_AND_TITLE, pageRequest)
                        .getContent().stream().map(PostResponseDto::getTitle).collect(Collectors.toList()))
                .containsExactly(
                        "title39", "title38", "title37",
                        "title36", "title35", "title34",
                        "title33", "title32", "title31",
                        "title30");
        Assertions.assertThat(postRepository.searchPost(searchMember, WRITER_AND_TITLE, pageRequest).getTotalElements()).isEqualTo(12L);
    }

    @Test
    @DisplayName("특정 번호 멤버의 게시물을 조회시키면 총 10개, 한 페이지에 10개의 결과가 반환된다. 결과는 최근 게시물 순으로 나온다.  ")
    public void getMembersPostTest() throws Exception{
        //given
        Member member = memberRepository.findByUsername("test")
                .orElseThrow(MemberNotFoundException::new);
        for(int index = 1; index <= 10; index++){
            postRepository.save(Post.builder()
                    .member(member)
                    .title("title" + index)
                    .content("content" + index).build());
            TimeUnit.SECONDS.sleep(1);
        }
        //when

        //then
        Assertions.assertThat(postRepository.getMembersPost(member.getId(), pageRequest)
                        .getContent().stream().map(PostResponseDto::getTitle).collect(Collectors.toList()))
                .containsExactly("title10", "title9", "title8",
                        "title7", "title6", "title5",
                        "title4", "title3", "title2",
                        "title1");
        Assertions.assertThat(postRepository.getMembersPost(member.getId(), pageRequest).getTotalElements()).isEqualTo(10L);
    }

    @Test
    @DisplayName("게시물을 수정하고 영속성 컨텍스트를 flush한 후 데이터를 불러오면, 변경감지로 수정된 데이터가 나온다. ")
    public void editTest() throws Exception{
        //given
        Member member = memberRepository.findByUsername("test")
                .orElseThrow(MemberNotFoundException::new);
        Post post = Post.builder().member(member).title("취업").content("하고싶다.").build();
        postRepository.save(post);
        //when
        post.editTitle("진짜 취업");
        post.editContent("너무 하고 싶습니다.");
        em.flush();
        Post findPost = postRepository.findById(post.getId()).orElseThrow(IllegalArgumentException::new);
        //then
        Assertions.assertThat(findPost.getTitle()).isEqualTo(post.getTitle());
        Assertions.assertThat(findPost.getContent()).isEqualTo(post.getContent());
    }
}
