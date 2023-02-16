package com.board.Board_Upgraded.controller.auth;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SignInRequestDto;
import com.board.Board_Upgraded.dto.token.TokenResponseDto;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@AutoConfigureMockMvc
public class ReissueTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void clearDB(){
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("헤더에 Access Token, Refresh Token이 있을 때 토큰이 재발행되어 헤더에 반환된다.")
    public void reissue_Success() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("testUser" + 1)
                .nickname("test" + 1)
                .email("test"  + 1 + "@test.com")
                .password("테스트" + 1)
                .passwordCheck("테스트" + 1)
                .build();
        authService.registerNewMember(registerRequestDto);
        TokenResponseDto tokenResponseDto = authService.signIn(
                SignInRequestDto.builder().username("testUser1").password("테스트1").build());
        TimeUnit.SECONDS.sleep(3);
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/api/auth/reissue")
                .header("Authorization", "Bearer ".concat(tokenResponseDto.getAccessToken()))
                .header("RefreshToken", "Bearer ".concat(tokenResponseDto.getRefreshToken())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("토큰이 유실되었을 경우, 400에러와 로그인 재요청을 반환")
    public void reissue_Fail() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("testUser" + 1)
                .nickname("test" + 1)
                .email("test"  + 1 + "@test.com")
                .password("테스트" + 1)
                .passwordCheck("테스트" + 1)
                .build();
        authService.registerNewMember(registerRequestDto);
        TokenResponseDto tokenResponseDto = authService.signIn(
                SignInRequestDto.builder().username("testUser1").password("테스트1").build());
        TimeUnit.SECONDS.sleep(3);
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/api/auth/reissue")
                .header("Authorization", "Bearer ".concat(tokenResponseDto.getAccessToken())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage").value("로그인 후 이용해주세요."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("")
    public void reissue_Fail2() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("testUser" + 1)
                .nickname("test" + 1)
                .email("test"  + 1 + "@test.com")
                .password("테스트" + 1)
                .passwordCheck("테스트" + 1)
                .build();
        authService.registerNewMember(registerRequestDto);
        TokenResponseDto tokenResponseDto = authService.signIn(
                SignInRequestDto.builder().username("testUser1").password("테스트1").build());
        TimeUnit.SECONDS.sleep(3);
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/api/auth/reissue")
                .header("Authorization", "Bearer ".concat(tokenResponseDto.getAccessToken())+"1")
                .header("RefreshToken", "Bearer ".concat(tokenResponseDto.getRefreshToken())))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print());
    }
}
