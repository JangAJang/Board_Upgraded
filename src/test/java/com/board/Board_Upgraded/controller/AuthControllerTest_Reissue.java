package com.board.Board_Upgraded.controller;

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
    @DisplayName("")
    public void reissue_Success() throws Exception{
        //given

        //expected

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
