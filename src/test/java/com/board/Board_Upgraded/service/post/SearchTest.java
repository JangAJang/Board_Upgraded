package com.board.Board_Upgraded.service.post;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.post.WritePostRequestDto;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
@Transactional
public class SearchTest {

    @Autowired
    private PostService postService;

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void makeMembers(){
        IntStream.range(1, 6).forEach(i -> authService.registerNewMember(RegisterRequestDto.builder()
                .username("test" + i)
                .nickname("test" + i)
                .email("test" + i + "@test.com")
                .password("test")
                .passwordCheck("test").build()));
        IntStream.range(11, 51).forEach( i->
                postService.write(WritePostRequestDto.builder()
                        .title("title" + (i - 10 + i%10))
                        .content("content" + ( i - 10 + i%10))
                        .build(), memberRepository.findByUsername("test" + i/10).orElseThrow(MemberNotFoundException::new)));
    }
}
