package com.board.Board_Upgraded.controller;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SignInRequestDto;
import com.board.Board_Upgraded.dto.token.TokenResponseDto;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class AuthControllerTest_Reissue {

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

    private String makeJson(Object object){
        try{
            return new ObjectMapper().writeValueAsString(object);
        }catch (JsonProcessingException e){
            e.printStackTrace();
            return "";
        }
    }
}
