package com.board.Board_Upgraded.service.post;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.post.PostResponseDto;
import com.board.Board_Upgraded.dto.post.SearchPostRequestDto;
import com.board.Board_Upgraded.dto.post.WritePostRequestDto;
import com.board.Board_Upgraded.entity.post.Post;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.repository.member.SearchPostType;
import com.board.Board_Upgraded.repository.post.PostRepository;
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

import java.util.List;
import java.util.stream.Collectors;
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

    @Autowired
    private PostRepository postRepository;

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
}
