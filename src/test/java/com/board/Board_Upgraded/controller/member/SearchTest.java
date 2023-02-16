package com.board.Board_Upgraded.controller.member;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.service.auth.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.IntStream;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void clearDB(){
        memberRepository.deleteAll();
        IntStream.range(1, 31)
                .forEach(index ->
                        authService.registerNewMember(RegisterRequestDto
                                .builder()
                                .username("test" + index)
                                .email("test" + index + "@test.com")
                                .nickname("test" + index)
                                .password("test" + index)
                                .passwordCheck("test" + index).build()));
    }

    private String makeJson(Object object){
        try{
            return new ObjectMapper().writeValueAsString(object);
        }catch (Exception e){
            return "";
        }
    }
}
