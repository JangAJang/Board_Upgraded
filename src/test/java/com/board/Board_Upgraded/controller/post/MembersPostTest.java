package com.board.Board_Upgraded.controller.post;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SignInRequestDto;
import com.board.Board_Upgraded.dto.post.WritePostRequestDto;
import com.board.Board_Upgraded.dto.token.TokenResponseDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.repository.post.PostRepository;
import com.board.Board_Upgraded.service.auth.AuthService;
import com.board.Board_Upgraded.service.post.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MembersPostTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuthService authService;

    @Autowired
    private PostService postService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void initData(){
        postRepository.deleteAll();
        memberRepository.deleteAll();
        IntStream.range(1, 11).forEach(
                i -> authService.registerNewMember(RegisterRequestDto.builder()
                        .username("test" + i)
                        .nickname("nick" + i)
                        .email("test"+i+"@test.com")
                        .password("pa")
                        .passwordCheck("pa").build())
        );
        IntStream.range(1, 101).forEach(i -> {
            Member member = memberRepository.findByUsername("test" + ((i-1)/10 + 1)).orElseThrow(MemberNotFoundException::new);
            postService.write(WritePostRequestDto.builder()
                    .title("제목"+i)
                    .content("내용"+i).build(), member);
        });
    }

    // 성공, 토큰이 없을 떄, 멤버가 없을 때, 입력변수가 없을 때
    @Test
    @DisplayName("토큰이 존재하고, 입력한 회원의 아이디가 존재할 때, 해당 멤버의 게시물을 반환한다. ")
    public void searchMemberSuccess() throws Exception{
        //given
        TokenResponseDto tokenResponseDto = authService.signIn(SignInRequestDto.builder()
                .username("test1")
                .password("pa").build());
        //expected
        mvc.perform(get("/api/posts?member=1&page=0")
                .header("Authorization", "Bearer ".concat(tokenResponseDto.getAccessToken()))
                .header("RefreshToken", "Bearer ".concat(tokenResponseDto.getRefreshToken()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.data.content.length()").value(10))
                .andDo(print());
    }
    @Test
    @DisplayName("멤버의 게시물을 조회할 때, 해당 멤버가 존재하지 않으면 404에러와 예외를 반환한다.")
    public void searchNoMember() throws Exception{
        //given
        TokenResponseDto tokenResponseDto = authService.signIn(SignInRequestDto.builder()
                .username("test1")
                .password("pa").build());
        //expected
        mvc.perform(get("/api/posts?member=30&page=0")
                .header("Authorization", "Bearer ".concat(tokenResponseDto.getAccessToken()))
                .header("RefreshToken", "Bearer ".concat(tokenResponseDto.getRefreshToken()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.result.failMessage").value("존재하지 않는 사용자이거나, 게시물이 존재하지 않습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("토큰이 존재하지 않을 때, 멤버의 게시물을 불러오게 할 때, 401에러를 반환한다.")
    public void searchNoToken() throws Exception{
        //given

        //expected
        mvc.perform(get("/api/posts?member=30&page=0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}
