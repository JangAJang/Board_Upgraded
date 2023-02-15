package com.board.Board_Upgraded.controller;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SignInRequestDto;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("로그인 기능 테스트")
public class AuthControllerTest_SignIn {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void cleanDB(){
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인을 성공했을 경우, 코드 200과 로그인 성공을 바디에 반환한다.")
    public void signIn_Success() throws Exception{
        //given
        authService.registerNewMember(RegisterRequestDto.builder()
                .username("testUser")
                .nickname("test")
                .email("test@test.com")
                .password("test")
                .passwordCheck("test").build());
        SignInRequestDto signInRequestDto = SignInRequestDto.builder()
                .username("testUser")
                .password("test")
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/auth/sign_in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(signInRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.data").value("로그인 성공"))
                .andDo(MockMvcResultHandlers.print());
    }

    private String makeJson(Object object){
        try{
            return new ObjectMapper().writeValueAsString(object);
        }catch(JsonProcessingException e){
            return "";
        }
    }
}
