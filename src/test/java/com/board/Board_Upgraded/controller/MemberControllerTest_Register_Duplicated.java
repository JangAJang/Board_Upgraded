package com.board.Board_Upgraded.controller;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class MemberControllerTest_Register_Duplicated {


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

    private static RegisterRequestDto makeTestRegister() {
        return RegisterRequestDto.builder()
                .username("test")
                .nickname("test")
                .email("test@test.com")
                .password("pass")
                .passwordCheck("pass")
                .build();
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

    @Test
    @DisplayName("닉네임이 중복되었을 때 400에러를 반환하며 body 에서 중복됨을 알려준다.")
    public void registerFail_DuplicatedNickname() throws Exception{
        //given
        memberService.registerNewMember(RegisterRequestDto.builder()
                .username("aaa")
                .nickname("test")
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage").value("이미 사용중인 닉네임입니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("이메일이 중복되었을 때 400에러를 반환하며 body 에서 중복됨을 알려준다.")
    public void registerFail_DuplicatedEmail() throws Exception{
        //given
        memberService.registerNewMember(RegisterRequestDto.builder()
                .username("aaa")
                .nickname("bbb")
                .email("test@test.com")
                .password("ccc")
                .passwordCheck("ccc")
                .build());
        RegisterRequestDto registerRequestDto = makeTestRegister();
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage").value("이미 사용중인 이메일입니다."))
                .andDo(MockMvcResultHandlers.print());
    }
}
