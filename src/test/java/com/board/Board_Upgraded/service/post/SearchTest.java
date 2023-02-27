package com.board.Board_Upgraded.service.post;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.post.PostResponseDto;
import com.board.Board_Upgraded.dto.post.SearchPostRequestDto;
import com.board.Board_Upgraded.dto.post.WritePostRequestDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.IntStream;

@SpringBootTest
@Transactional
public class SearchTest {

    @Autowired
    private PostService postService;

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void makeMembers(){
        IntStream.range(1, 6).forEach(i -> authService.registerNewMember(RegisterRequestDto.builder()
                .username("test" + i)
                .nickname("test" + i)
                .email("test" + i + "@test.com")
                .password("test")
                .passwordCheck("test").build()));
        IntStream.range(11, 51).forEach( i->
                postService.write(WritePostRequestDto.builder()
                        .title("title" + i)
                        .content("content" + i)
                        .build(), memberRepository.findByUsername("test" + i/10).orElseThrow(MemberNotFoundException::new)));
    }

    @Test
    @DisplayName("제목에 5를 입력하면 11~50까지 만들어진 게시물 중에 50, 45, 35, 25, 15가 나온다.(게시글 생성의 역순)")
    public void searchByTitleTest() throws Exception{
        //given
        SearchPostRequestDto searchPostRequestDto = new SearchPostRequestDto("5");
        PageRequest pageRequest = PageRequest.of(0, 10);
        //when

        //then
        Page<PostResponseDto> responseDtoPage = postService.search(searchPostRequestDto, pageRequest, SearchPostType.TITLE);
        Assertions.assertThat(responseDtoPage.getTotalElements()).isEqualTo(5L);
        Assertions.assertThat(responseDtoPage.getContent().stream().map(PostResponseDto::getTitle))
                .containsExactly("title50", "title45", "title35", "title25", "title15");
    }

    @Test
    @DisplayName("내용에 4를 입력하면 11~50까지 만들어진 게시물 중에 49, 48, 47, 46, 45, 44, 43, 42, 41, 40이 나온다.(게시글 생성의 역순) 다음 페이지에 34, 24, 14가 나온다.")
    public void searchByContentTest() throws Exception{
        //given
        SearchPostRequestDto searchPostRequestDto = new SearchPostRequestDto("4");
        PageRequest pageRequest0 = PageRequest.of(0, 10);
        PageRequest pageRequest1 = PageRequest.of(1, 10);
        //when

        //then
        Page<PostResponseDto> responseDtoPage = postService.search(searchPostRequestDto, pageRequest0, SearchPostType.CONTENT);
        Assertions.assertThat(responseDtoPage.getTotalElements()).isEqualTo(13L);
        Assertions.assertThat(responseDtoPage.getContent().stream().map(PostResponseDto::getTitle))
                .containsExactly("title49", "title48", "title47", "title46", "title45",
                        "title44", "title43", "title42", "title41", "title40");
        Assertions.assertThat(postService.search(searchPostRequestDto, pageRequest1, SearchPostType.CONTENT)
                .getContent().stream().map(PostResponseDto::getTitle)).containsExactly(
                "title34", "title24", "title14"
        );
    }

    @Test
    @DisplayName("작성자에 2를 입력하면 test2 닉네임의 게시물 10개가 최근 게시/수정된 순으로 반환된다. ")
    public void searchByWriterTest() throws Exception{
        //given
        SearchPostRequestDto searchPostRequestDto = new SearchPostRequestDto("2");
        PageRequest pageRequest0 = PageRequest.of(0, 10);
        //when
        Page<PostResponseDto> result = postService.search(searchPostRequestDto, pageRequest0, SearchPostType.WRITER);
        //then
        Assertions.assertThat(result.getTotalElements()).isEqualTo(10L);
        Assertions.assertThat(result.getContent().stream().map(PostResponseDto::getTitle))
                .containsExactly("title29", "title28", "title27",
                        "title26", "title25", "title24",
                        "title23", "title22", "title21",
                        "title20");
    }

    @Test
    @DisplayName("제목과 내용에 5를 입력하면, 50, 45, 35, 25, 15번의 5개 게시물이 출력된다. (즉, 제목에 5가 있고 내용에 5가 있더라도 중복되지 않는다)")
    public void searchByTitleAndContent() throws Exception{
        //given
        SearchPostRequestDto searchPostRequestDto = new SearchPostRequestDto("5");
        PageRequest pageRequest = PageRequest.of(0, 10);
        //when

        //then
        Page<PostResponseDto> responseDtoPage = postService.search(searchPostRequestDto, pageRequest, SearchPostType.TITLE_AND_CONTENT);
        Assertions.assertThat(responseDtoPage.getTotalElements()).isEqualTo(5L);
        Assertions.assertThat(responseDtoPage.getContent().stream().map(PostResponseDto::getTitle))
                .containsExactly("title50", "title45", "title35", "title25", "title15");
    }

    @Test
    @DisplayName("작성자와 제목에 3을 입력하면, 3번 작성자가 작성한 30~39번 게시물과, 13, 23, 43번 게시물이 최근 게시/수정된 순서로 나온다 (즉, 작성자명에 3이 있고 제목에 3이 있어도 중복되지 않는다.)")
    public void searchByWriterAndTitle() throws Exception{
        //given
        SearchPostRequestDto searchPostRequestDto = new SearchPostRequestDto("3");
        //when
        Page<PostResponseDto> responseDtoPage0 = postService.search(
                searchPostRequestDto, PageRequest.of(0, 10), SearchPostType.WRITER_AND_TITLE);
        Page<PostResponseDto> responseDtoPage1 = postService.search(
                searchPostRequestDto, PageRequest.of(1, 10), SearchPostType.WRITER_AND_TITLE);
        //then
        Assertions.assertThat(responseDtoPage0.getTotalElements()).isEqualTo(13L);
        Assertions.assertThat(responseDtoPage0.getContent().stream().map(PostResponseDto::getTitle))
                .containsExactly("title43", "title39", "title38", "title37", "title36",
                        "title35", "title34", "title33", "title32", "title31");
        Assertions.assertThat(responseDtoPage1.getContent().stream().map(PostResponseDto::getTitle))
                .containsExactly("title30", "title23", "title13");
    }
}
