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
        authService.registerNewMember(RegisterRequestDto.builder()
                .username("testUser")
                .nickname("test")
                .email("test@test.com")
                .password("test")
                .passwordCheck("test").build());
    }

    @Test
    @DisplayName("로그인을 성공했을 경우, 코드 200과 로그인 성공을 바디에 반환한다.")
    public void signIn_Success() throws Exception{
        //given
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.data.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.data.accessToken").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.data.refreshToken").isString())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 로그인을 요청할 때, 404에러와 회원이 없음을 바디에 반환한다.")
    public void signIn_Fail_NOT_FOUND() throws Exception{
        //given
        SignInRequestDto signInRequestDto = SignInRequestDto.builder()
                .username("testUser1")
                .password("test")
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/auth/sign_in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(signInRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage").value("해당 사용자를 찾을 수 없습니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원의 아이디가 존재할 때, 비밀번호가 틀리면 400에러와 함께 비밀번호가 틀렸음을 반환한다.")
    public void signIn_Fail_PasswordWrong() throws Exception{
        //given
        SignInRequestDto signInRequestDto = SignInRequestDto.builder()
                .username("testUser")
                .password("test1")
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/auth/sign_in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(signInRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage").value("비밀번호가 일치하지 않습니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("입력중에 아이디가 null값일 때 validation으로 예외처리된다.")
    public void signIn_Fail_NullInput() throws Exception{
        //given
        SignInRequestDto signInRequestDto = SignInRequestDto.builder()
                .password("test1")
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/auth/sign_in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(signInRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage").value("아이디를 입력해야 합니다."))
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
