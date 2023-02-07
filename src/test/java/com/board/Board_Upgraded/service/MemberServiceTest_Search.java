package com.board.Board_Upgraded.service;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberServiceTest_Search {

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void initData(){
        for(int index = 1; index <= 10; index++){
            RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                    .username("test" + index)
                    .nickname("test" + index)
                    .email("test" + index + "@test.com")
                    .password("test")
                    .passwordCheck("test")
                    .build();
            memberService.registerNewMember(registerRequestDto);
        }
    }


}
