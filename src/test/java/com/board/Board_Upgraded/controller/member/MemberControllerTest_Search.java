package com.board.Board_Upgraded.controller.member;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SearchMemberDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.service.MemberService;
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

import static com.board.Board_Upgraded.repository.member.SearchType.EXACT;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest_Search {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void makeNewData(){
        memberRepository.deleteAll();
        IntStream.range(1, 31)
                .forEach( i -> memberService.registerNewMember(
                        RegisterRequestDto.builder()
                                .username("test"+i)
                                .nickname("test"+i)
                                .email("test"+i+"@test.com")
                                .password("test")
                                .passwordCheck("test")
                                .build()));
    }

    @Test
    @DisplayName("로그인이 되어있지 않은 상태에서 조회를 시행하면, 권한이 없어 401에러를 반환한다. ")
    public void searchTest_Unauthorized() throws Exception{
        //given
        SearchMemberDto searchMemberDto = SearchMemberDto.builder()
                .username("")
                .nickname("")
                .email("")
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.get("/members/{type}?page=0", EXACT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(makeJson(searchMemberDto))))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    private String makeJson(Object object){
        try{
            return new ObjectMapper().writeValueAsString(object);
        }catch(Exception e){
            return "";
        }
    }
}
