package com.board.Board_Upgraded.controller.post;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SignInRequestDto;
import com.board.Board_Upgraded.dto.post.WritePostRequestDto;
import com.board.Board_Upgraded.dto.token.TokenResponseDto;
import com.board.Board_Upgraded.entity.post.Post;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
import com.board.Board_Upgraded.exception.post.PostNotFoundException;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DeleteTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PostService postService;

    @Autowired
    private AuthService authService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void initializeTestEnvironment(){
        clearDB();
        createTestData();
    }

    @Test
    @DisplayName("게시물을 삭제할 때, 토큰이 있는 상태로 내가 만든 게시물을 삭제요청 하면 정상적으로 삭제된다.")
    public void deletePostTest() throws Exception{
        //given
        Post post = postRepository.findByTitle("제목입니다.").orElseThrow(PostNotFoundException::new);
        TokenResponseDto tokenResponseDto = authService.signIn(SignInRequestDto.builder()
                .username("test")
                .password("test").build());
        //expected
        mvc.perform(MockMvcRequestBuilders.delete("/api/posts/delete?id="+post.getId())
                        .header("Authorization", "Bearer ".concat(tokenResponseDto.getAccessToken()))
                        .header("RefreshToken", "Bearer ".concat(tokenResponseDto.getRefreshToken()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.data").value("삭제 완료"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("토큰이 없을 때, 삭제를 요청하면 401에러를 반환한다")
    public void deletePost_NoToken() throws Exception{
        //given
        Post post = postRepository.findByTitle("제목입니다.").orElseThrow(PostNotFoundException::new);
        TokenResponseDto tokenResponseDto = authService.signIn(SignInRequestDto.builder()
                .username("test")
                .password("test").build());
        //expected
        mvc.perform(MockMvcRequestBuilders.delete("/api/posts/delete?id="+post.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print());
    }

    void clearDB(){
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    void createTestData(){
        authService.registerNewMember(RegisterRequestDto.builder()
                .username("test")
                .nickname("test")
                .email("test@test.com")
                .password("test")
                .passwordCheck("test").build());
        postService.write(WritePostRequestDto.builder()
                        .title("제목입니다.")
                        .content("내용입니다.").build(),
                memberRepository.findByUsername("test").orElseThrow(MemberNotFoundException::new));
    }
}
