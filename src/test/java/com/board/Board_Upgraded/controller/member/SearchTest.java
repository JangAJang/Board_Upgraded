package com.board.Board_Upgraded.controller.member;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SearchMemberDto;
import com.board.Board_Upgraded.dto.member.SignInRequestDto;
import com.board.Board_Upgraded.dto.token.TokenResponseDto;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.service.auth.AuthService;
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

import java.util.stream.IntStream;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void clearDB(){
        memberRepository.deleteAll();
        IntStream.range(1, 31)
                .forEach(index ->
                        authService.registerNewMember(RegisterRequestDto
                                .builder()
                                .username("test" + index)
                                .email("test" + index + "@test.com")
                                .nickname("test" + index)
                                .password("test" + index)
                                .passwordCheck("test" + index).build()));
    }

    @Test
    @DisplayName("토큰이 없으면, 401에러를 반환한다.")
    public void searchNoToken_FAIL() throws Exception{
        //given
        SearchMemberDto searchMemberDto = SearchMemberDto.builder()
                .username(null)
                .email(null)
                .nickname(null)
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.get("/api/members/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(searchMemberDto)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("검색 파라미터가 전부 null이면 400에러와, 검색조건이 없음을 반환한다.")
    public void searchAllNull_FAIL() throws Exception{
        //given
        TokenResponseDto tokenResponseDto = authService.signIn(SignInRequestDto.builder()
                .username("test1")
                .password("test1").build());
        SearchMemberDto searchMemberDto = SearchMemberDto.builder()
                .username(null)
                .email(null)
                .nickname(null)
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.get("/api/members/search")
                .header("Authorization", "Bearer ".concat(tokenResponseDto.getAccessToken()))
                .header("RefreshToken", "Bearer ".concat(tokenResponseDto.getRefreshToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(searchMemberDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("test라는 아이디를 기준으로 1페이지를 검색하면 test1~test10이 출력된다.")
    public void searchAll_Success() throws Exception{
        //given
        TokenResponseDto tokenResponseDto = authService.signIn(SignInRequestDto.builder()
                .username("test1")
                .password("test1").build());
        SearchMemberDto searchMemberDto = SearchMemberDto.builder()
                .username("test")
                .email(null)
                .nickname(null)
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.get("/api/members/search?page={}", 1)
                        .header("Authorization", "Bearer ".concat(tokenResponseDto.getAccessToken()))
                        .header("RefreshToken", "Bearer ".concat(tokenResponseDto.getRefreshToken()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(searchMemberDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.data.content.length()").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.data.content.[0].username").value("test1"))
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
