package com.board.Board_Upgraded.controller;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void cleanDB(){
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입에 성공하면 body에 회원가입 완료 문구가 출력된다. ")
    public void registerSuccess() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("testUser")
                .nickname("test")
                .email("test@test.com")
                .password("test")
                .passwordCheck("test").build();
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/auth/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.data").value("회원가입 완료"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원가입 요청에 null값이 존재한다면 400(Bad Request)에러를 반환한다.")
    public void registerFail_ValidationException_Null() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .nickname("test")
                .email("test@test.com")
                .password("test")
                .passwordCheck("test").build();
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/auth/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage")
                        .value("아이디를 입력해야 합니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원가입 요청에 공백값이 존재한다면 400(Bad Request)에러를 반환한다.")
    public void registerFail_ValidationException_Empty() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username(" ")
                .nickname("test")
                .email("test@test.com")
                .password("test")
                .passwordCheck("test").build();
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/auth/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage")
                        .value("아이디를 입력해야 합니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("비밀번호가 서로 다를 때, 400에러를 반환하며 일치하지 않음을 body에 출력시킨다.")
    public void registerFail_PasswordNotEqual() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("testUser")
                .nickname("test")
                .email("test@test.com")
                .password("test")
                .passwordCheck("test1").build();
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/auth/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage")
                        .value("비밀번호가 일치하지 않습니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("이메일 형식이 올바르지 않을 때 400에러를 반환하며 일치하지 않음을 body에 출력시킨다.")
    public void registerFail_EmailFormat() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("testUser")
                .nickname("test")
                .email("testtest.com")
                .password("test")
                .passwordCheck("test").build();
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/auth/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage")
                        .value("올바르지 않은 이메일 형식입니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    private String makeJson(Object object){
        try{
            return new ObjectMapper().writeValueAsString(object);
        }catch(Exception e) {
            System.out.println("에러발생");
            return "";
        }
    }
}
