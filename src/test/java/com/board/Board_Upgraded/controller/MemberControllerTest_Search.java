package com.board.Board_Upgraded.controller;

import com.board.Board_Upgraded.dto.member.SearchMemberDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.repository.member.MemberRepository;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.IntStream;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest_Search {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void makeNewData(){
        memberRepository.deleteAll();
        IntStream.range(1, 31)
                .forEach( i -> memberRepository.save(
                        Member.builder()
                                .username("test"+i)
                                .nickname("test"+i)
                                .email("test"+i+"@test.com")
                                .password("test")
                                .password("test")
                                .build()));
    }

    @Test
    @DisplayName("")
    public void searchExceptionTest() throws Exception{
        //given
        SearchMemberDto searchMemberDto = SearchMemberDto.builder()
                .username("")
                .nickname("")
                .email("")
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.get("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(makeJson(searchMemberDto))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String makeJson(Object object){
        try{
            return new ObjectMapper().writeValueAsString(object);
        }catch(Exception e){
            return "";
        }
    }
}
