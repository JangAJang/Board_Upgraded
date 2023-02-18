package com.board.Board_Upgraded.controller.member;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SignInRequestDto;
import com.board.Board_Upgraded.dto.token.TokenResponseDto;
import com.board.Board_Upgraded.service.auth.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class DeleteTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("회원 삭제를 성공하면 성공문구와 200코드를 반환한다.")
    public void delete_Success() throws Exception{
        //given
        authService.registerNewMember(RegisterRequestDto.builder()
                .username("test")
                .nickname("test")
                .email("test@test.com")
                .password("test")
                .passwordCheck("test").build());
        TokenResponseDto tokenResponseDto = authService.signIn(SignInRequestDto.builder()
                .username("test")
                .password("test").build());
        //expected
        mvc.perform(MockMvcRequestBuilders.delete("/api/members/delete")
                        .header("Authorization", "Bearer ".concat(tokenResponseDto.getAccessToken()))
                        .header("RefreshToken", "Bearer ".concat(tokenResponseDto.getRefreshToken())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.data").value(""));
    }
}
