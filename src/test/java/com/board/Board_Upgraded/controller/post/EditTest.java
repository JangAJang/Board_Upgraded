package com.board.Board_Upgraded.controller.post;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SignInRequestDto;
import com.board.Board_Upgraded.dto.post.EditPostRequestDto;
import com.board.Board_Upgraded.dto.post.WritePostRequestDto;
import com.board.Board_Upgraded.dto.token.TokenResponseDto;
import com.board.Board_Upgraded.entity.post.Post;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
import com.board.Board_Upgraded.exception.post.PostNotFoundException;
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

@SpringBootTest
@AutoConfigureMockMvc
public class EditTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private PostService postService;

    @BeforeEach
    void initializeTestEnvironment(){
        clearDB();
        createTestData();
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

    @Test
    @DisplayName("게시물을 수정할 때, 회원권한이 있으면 정상적으로 게시물을 수정한다. ")
    @Transactional
    public void editPostTest() throws Exception{
        //given
        Post post = postRepository.findByTitle("제목입니다.").orElseThrow(PostNotFoundException::new);
        TokenResponseDto tokenResponseDto = authService.signIn(SignInRequestDto.builder()
                .username("test")
                .password("test").build());
        EditPostRequestDto editPostRequestDto = EditPostRequestDto.builder()
                .title("바뀐 제목입니다.")
                .content("바뀐 내용입니다.")
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.patch("/api/posts/edit?id="+post.getId())
                        .header("Authorization", "Bearer ".concat(tokenResponseDto.getAccessToken()))
                        .header("RefreshToken", "Bearer ".concat(tokenResponseDto.getRefreshToken()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(editPostRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    private String makeJson(Object object){
        try{
            return new ObjectMapper().writeValueAsString(object);
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
