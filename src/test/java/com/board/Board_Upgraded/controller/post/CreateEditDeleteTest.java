package com.board.Board_Upgraded.controller.post;

import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.repository.post.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class CreateEditDeleteTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clearDB(){
        memberRepository.deleteAll();
        postRepository.deleteAll();
    }



    private String makeJson(Object object){
        try{
            return new ObjectMapper().writeValueAsString(object);
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
