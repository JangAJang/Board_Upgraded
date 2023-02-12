package com.board.Board_Upgraded.controller;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {


    @Autowired
    private MockMvc mvc;

    private String makeJson(Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    @Test
    @DisplayName("회원가입을 성공하면 success register가 반환된다.")
    public void registerSuccessTest() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("test")
                .nickname("test")
                .email("test@test.com")
                .password("pass")
                .passwordCheck("pass")
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("success register"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("아이디가 null값이면 400에러를 날린다.")
    public void registerFail_NullUsername() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username(null)
                .nickname("test")
                .email("test@test.com")
                .password("pass")
                .passwordCheck("pass")
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("닉네임이 null값이면 400에러를 날린다.")
    public void registerFail_NullNickname() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("test")
                .nickname(null)
                .email("test@test.com")
                .password("pass")
                .passwordCheck("pass")
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("이메일이 null값이면 400에러를 날린다.")
    public void registerFail_NullEmail() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("test")
                .nickname("test")
                .email(null)
                .password("pass")
                .passwordCheck("pass")
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("비밀번호가 null값이면 400에러를 날린다.")
    public void registerFail_NullPassword() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("test")
                .nickname("test")
                .email("test@test.com")
                .password(null)
                .passwordCheck("pass")
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("비밀번호 재입력이 null값이면 400에러를 날린다.")
    public void registerFail_NullPasswordCheck() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("test")
                .nickname("test")
                .email("test@test.com")
                .password("pass")
                .passwordCheck(null)
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
}