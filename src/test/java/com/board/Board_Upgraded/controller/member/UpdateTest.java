package com.board.Board_Upgraded.controller.member;

import com.board.Board_Upgraded.dto.member.ChangeNicknameRequestDto;
import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SignInRequestDto;
import com.board.Board_Upgraded.dto.token.TokenResponseDto;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.service.auth.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
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
public class UpdateTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("업데이트를 성공하면 200코드와 성공했음을 알리는 문자열을 반환한다.")
    public void updateNickname_Success() throws Exception{
        //given
        authService.registerNewMember(RegisterRequestDto.builder()
                        .username("test")
                        .nickname("test")
                        .email("test@test.com")
                        .password("test")
                        .passwordCheck("test")
                .build());
        TokenResponseDto token = authService.signIn(SignInRequestDto.builder()
                .username("test")
                .password("test").build());
        ChangeNicknameRequestDto changeNicknameRequestDto = new ChangeNicknameRequestDto("nickname");
        //expected
        mvc.perform(MockMvcRequestBuilders.patch("/api/members/update/nickname")
                .header("Authorization", "Bearer ".concat(token.getAccessToken()))
                .header("RefreshToken", "Bearer ".concat(token.getRefreshToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(changeNicknameRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        Assertions.assertThat(memberRepository.findByUsername("test").get().getNickname())
                .isEqualTo("nickname");
    }

    private String makeJson(Object object){
        try{
            return new ObjectMapper().writeValueAsString(object);
        }catch (Exception e){
            return "";
        }
    }


}
