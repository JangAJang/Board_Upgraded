package com.board.Board_Upgraded.controller;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    private String makeJson(Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    @BeforeEach
    void clearDB(){
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입을 성공하면 success register가 반환된다.")
    public void registerSuccessTest() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = makeTestRegister();
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("success register"))
                .andDo(MockMvcResultHandlers.print());
    }

    private static RegisterRequestDto makeTestRegister() {
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("test")
                .nickname("test")
                .email("test@test.com")
                .password("pass")
                .passwordCheck("pass")
                .build();
        return registerRequestDto;
    }

    @Test
    @DisplayName("아이디가 null값이면 400에러를 날린다.")
    public void registerFail_NullUsername() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = makeTestRegister();
        registerRequestDto.setUsername(null);
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage").value("아이디를 입력해야 합니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("닉네임이 null값이면 400에러를 날린다.")
    public void registerFail_NullNickname() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = makeTestRegister();
        registerRequestDto.setNickname(null);
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage").value("닉네임을 입력해야 합니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("이메일이 null값이면 400에러를 날린다.")
    public void registerFail_NullEmail() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = makeTestRegister();
        registerRequestDto.setEmail(null);
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage").value("이메일을 입력해야 합니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("비밀번호가 null값이면 400에러를 날린다.")
    public void registerFail_NullPassword() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = makeTestRegister();
        registerRequestDto.setPassword(null);
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage").value("비밀번호를 입력해야 합니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("비밀번호 재입력이 null 값이면 400에러를 날린다.")
    public void registerFail_NullPasswordCheck() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = makeTestRegister();
        registerRequestDto.setPasswordCheck(null);
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage").value("비밀번호를 다시 입력해야 합니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("아이디가 중복되었을 때 404에러를 반환하며 body 에서 중복됨을 알려준다.")
    public void registerFail_DuplicatedUsername() throws Exception{
        //given
        memberService.registerNewMember(RegisterRequestDto.builder()
                .username("test")
                .nickname("aaa")
                .email("bbb@bbb.com")
                .password("ccc")
                .passwordCheck("ccc")
                .build());
        RegisterRequestDto registerRequestDto = makeTestRegister();
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage").value("이미 사용중인 아이디입니다."))
                .andDo(MockMvcResultHandlers.print());
    }
}