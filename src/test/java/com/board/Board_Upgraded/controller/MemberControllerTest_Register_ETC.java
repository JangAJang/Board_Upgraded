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

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest_Register_ETC {

    @Autowired
    private MockMvc mvc;

    private String makeJson(Object object){
        try{
            return new ObjectMapper().writeValueAsString(object);
        }catch(JsonProcessingException e){
            return "";
        }
    }

    public RegisterRequestDto makeTest(){
        return RegisterRequestDto.builder()
                .username("test")
                .nickname("test")
                .email("test@test.com")
                .password("test")
                .passwordCheck("test")
                .build();
    }

    @Test
    @DisplayName("이메일 형식이 test 일 때 400에러를 발생시키며 형식이상을 body 에 출력시킨다")
    public void registerFail_EmailFormat1() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = makeTest();
        registerRequestDto.setEmail("test");
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage").value("올바르지 않은 이메일 형식입니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("이메일 형식이 test@ 일 때 400에러를 발생시키며 형식이상을 body 에 출력시킨다")
    public void registerFail_EmailFormat2() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = makeTest();
        registerRequestDto.setEmail("test@");
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage").value("올바르지 않은 이메일 형식입니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("이메일 형식이 test@test 일 때 400에러를 발생시키며 형식이상을 body 에 출력시킨다")
    public void registerFail_EmailFormat3() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = makeTest();
        registerRequestDto.setEmail("test@test");
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage").value("올바르지 않은 이메일 형식입니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("이메일 형식이 test@test. 일 때 400에러를 발생시키며 형식이상을 body 에 출력시킨다")
    public void registerFail_EmailFormat4() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = makeTest();
        registerRequestDto.setEmail("test@test.");
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage").value("올바르지 않은 이메일 형식입니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("비밀번호가 서로 일치하지 않은 경우 400에러를 발생시키며 형식이상을 body 에 출력시킨다")
    public void registerFail_PasswordMissMatch() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = makeTest();
        registerRequestDto.setPassword("test1");
        //expected
        mvc.perform(MockMvcRequestBuilders.post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(registerRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage").value("비밀번호가 일치하지 않습니다."))
                .andDo(MockMvcResultHandlers.print());
    }
}
