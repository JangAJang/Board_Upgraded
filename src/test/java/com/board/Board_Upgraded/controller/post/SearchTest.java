package com.board.Board_Upgraded.controller.post;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SignInRequestDto;
import com.board.Board_Upgraded.dto.post.SearchPostRequestDto;
import com.board.Board_Upgraded.dto.post.WritePostRequestDto;
import com.board.Board_Upgraded.dto.token.TokenResponseDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.repository.post.PostRepository;
import com.board.Board_Upgraded.service.auth.AuthService;
import com.board.Board_Upgraded.service.post.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SearchTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private AuthService authService;

    @BeforeEach
    void initData(){
        postRepository.deleteAll();
        memberRepository.deleteAll();
        IntStream.range(1, 11).forEach(i -> {
            authService.registerNewMember(RegisterRequestDto.builder()
                    .username("test" + i)
                    .nickname("test" + i)
                    .email("test" + i + "@test.com")
                    .password("test")
                    .passwordCheck("test").build());
        });
        IntStream.range(1, 101).forEach(i -> {
            Member member = memberRepository.findByUsername("test" + ((i-1)/10 + 1)).orElseThrow(MemberNotFoundException::new);
            postService.write(WritePostRequestDto.builder()
                    .title("제목"+i)
                    .content("내용"+i).build(), member);
        });
    }

    @Test
    @DisplayName("제목에 10을 이용해 검색하면, 100, 10순서로 데이터가 반환된다.")
    public void searchTitle() throws Exception{
        //given
        authService.registerNewMember(RegisterRequestDto.builder()
                .username("user")
                .nickname("nick")
                .email("em@em.com")
                .password("pass")
                .passwordCheck("pass").build());
        TokenResponseDto tokenResponseDto = authService.signIn(SignInRequestDto.builder()
                .username("user")
                .password("pass").build());
        SearchPostRequestDto searchPostRequestDto = new SearchPostRequestDto("10");
        //expected
        mvc.perform(MockMvcRequestBuilders.get("/api/posts/search?page=0&type=TITLE")
                .header("Authorization", "Bearer ".concat(tokenResponseDto.getAccessToken()))
                .header("RefreshToken", "Bearer ".concat(tokenResponseDto.getRefreshToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(searchPostRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.data.content.length()").value(2))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("제목에 10을 이용해 검색하면, 100, 10순서로 데이터가 반환된다.")
    public void searchContent() throws Exception{
        //given
        authService.registerNewMember(RegisterRequestDto.builder()
                .username("user")
                .nickname("nick")
                .email("em@em.com")
                .password("pass")
                .passwordCheck("pass").build());
        TokenResponseDto tokenResponseDto = authService.signIn(SignInRequestDto.builder()
                .username("user")
                .password("pass").build());
        SearchPostRequestDto searchPostRequestDto = new SearchPostRequestDto("10");
        //expected
        mvc.perform(MockMvcRequestBuilders.get("/api/posts/search?page=0&type=CONTENT")
                .header("Authorization", "Bearer ".concat(tokenResponseDto.getAccessToken()))
                .header("RefreshToken", "Bearer ".concat(tokenResponseDto.getRefreshToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(searchPostRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.data.content.length()").value(2))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("작성자에 10을 이용해 검색하면, 100~91순서로 데이터가 반환된다.")
    public void searchWriter() throws Exception{
        //given
        authService.registerNewMember(RegisterRequestDto.builder()
                .username("user")
                .nickname("nick")
                .email("em@em.com")
                .password("pass")
                .passwordCheck("pass").build());
        TokenResponseDto tokenResponseDto = authService.signIn(SignInRequestDto.builder()
                .username("user")
                .password("pass").build());
        SearchPostRequestDto searchPostRequestDto = new SearchPostRequestDto("10");
        //expected
        mvc.perform(MockMvcRequestBuilders.get("/api/posts/search?page=0&type=WRITER")
                .header("Authorization", "Bearer ".concat(tokenResponseDto.getAccessToken()))
                .header("RefreshToken", "Bearer ".concat(tokenResponseDto.getRefreshToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(searchPostRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.data.content.length()").value(10))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("작성자와 제목에 10을 이용해 검색하면, 100~91과 10순서로 데이터가 반환된다. 페이징으로 10~91만 반환된다. ")
    public void searchWriterAndTitle() throws Exception{
        //given
        authService.registerNewMember(RegisterRequestDto.builder()
                .username("user")
                .nickname("nick")
                .email("em@em.com")
                .password("pass")
                .passwordCheck("pass").build());
        TokenResponseDto tokenResponseDto = authService.signIn(SignInRequestDto.builder()
                .username("user")
                .password("pass").build());
        SearchPostRequestDto searchPostRequestDto = new SearchPostRequestDto("10");
        //expected
        mvc.perform(MockMvcRequestBuilders.get("/api/posts/search?page=0&type=WRITER_AND_TITLE")
                .header("Authorization", "Bearer ".concat(tokenResponseDto.getAccessToken()))
                .header("RefreshToken", "Bearer ".concat(tokenResponseDto.getRefreshToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(searchPostRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.data.content.length()").value(10))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("제목과 내용에 10을 이용해 검색하면, 100, 10순서로 데이터가 반환된다.")
    public void searchTitleAndContent() throws Exception{
        //given
        authService.registerNewMember(RegisterRequestDto.builder()
                .username("user")
                .nickname("nick")
                .email("em@em.com")
                .password("pass")
                .passwordCheck("pass").build());
        TokenResponseDto tokenResponseDto = authService.signIn(SignInRequestDto.builder()
                .username("user")
                .password("pass").build());
        SearchPostRequestDto searchPostRequestDto = new SearchPostRequestDto("10");
        //expected
        mvc.perform(MockMvcRequestBuilders.get("/api/posts/search?page=0&type=TITLE_AND_CONTENT")
                .header("Authorization", "Bearer ".concat(tokenResponseDto.getAccessToken()))
                .header("RefreshToken", "Bearer ".concat(tokenResponseDto.getRefreshToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(searchPostRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.data.content.length()").value(2))
                .andDo(MockMvcResultHandlers.print());
    }

    private String makeJson(Object object){
        try{
            return new ObjectMapper().writeValueAsString(object);
        }catch (Exception e){
            return "";
        }
    }
}
