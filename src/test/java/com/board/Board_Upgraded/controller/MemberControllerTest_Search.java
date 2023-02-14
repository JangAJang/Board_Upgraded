package com.board.Board_Upgraded.controller;

import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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
}
