package com.board.Board_Upgraded.controller.member;

import com.board.Board_Upgraded.domain.member.Username;
import com.board.Board_Upgraded.dto.member.EditMemberRequestDto;
import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SignInRequestDto;
import com.board.Board_Upgraded.dto.token.TokenResponseDto;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.service.auth.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class EditTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void clearDB(){
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("닉네임 변경을 요청할 떄, 성공하면 200코드와 수정에 성공했음을 반환해준다. ")
    public void editNickname_Success() throws Exception{
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
        EditMemberRequestDto editMemberRequestDto = EditMemberRequestDto.builder()
                .nickname("newNick")
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.patch("/api/members/edit")
                .header("Authorization", "Bearer ".concat(tokenResponseDto.getAccessToken()))
                .header("RefreshToken", "Bearer ".concat(tokenResponseDto.getRefreshToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(editMemberRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.data").value("수정을 성공했습니다."))
                .andDo(MockMvcResultHandlers.print());
        Assertions.assertThat(memberRepository.findByUsername(new Username("test")).get().getNickname()).isEqualTo("newNick");
    }

    @Test
    @DisplayName("닉네임 변경을 요청할 떄,이미 사용중이거나 다른 사용자가 사용중이면 400에러와 이미 사용중임을 반환한다. 사용자의 닉네임은 변경되지 않는다")
    public void editNickname_Fail_InUse() throws Exception{
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
        EditMemberRequestDto editMemberRequestDto = EditMemberRequestDto.builder()
                .nickname("test")
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.patch("/api/members/edit")
                .header("Authorization", "Bearer ".concat(tokenResponseDto.getAccessToken()))
                .header("RefreshToken", "Bearer ".concat(tokenResponseDto.getRefreshToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(editMemberRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage").value("이미 사용중인 닉네임입니다."))
                .andDo(MockMvcResultHandlers.print());
        Assertions.assertThat(memberRepository.findByUsername(new Username("test")).get().getNickname()).isEqualTo("test");
    }

    @Test
    @DisplayName("이메일 변경을 요청할 떄, 성공하면 200코드와 수정에 성공했음을 반환해준다. ")
    public void editEmail_Success() throws Exception{
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
        EditMemberRequestDto editMemberRequestDto = EditMemberRequestDto.builder()
                .email("newMail@test.com")
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.patch("/api/members/edit")
                .header("Authorization", "Bearer ".concat(tokenResponseDto.getAccessToken()))
                .header("RefreshToken", "Bearer ".concat(tokenResponseDto.getRefreshToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(editMemberRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.data").value("수정을 성공했습니다."))
                .andDo(MockMvcResultHandlers.print());
        Assertions.assertThat(memberRepository.findByUsername(new Username("test")).get().getEmail()).isEqualTo("newMail@test.com");
    }

    @Test
    @DisplayName("이미 사용중인 이메일이면 400에러와 함께 예외 문구를 반환하며 이메일은 바뀌지 않는다.")
    public void editEmail_Fail_InUse() throws Exception{
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
        EditMemberRequestDto editMemberRequestDto = EditMemberRequestDto.builder()
                .email("test@test.com")
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.patch("/api/members/edit")
                .header("Authorization", "Bearer ".concat(tokenResponseDto.getAccessToken()))
                .header("RefreshToken", "Bearer ".concat(tokenResponseDto.getRefreshToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(editMemberRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage").value("이미 사용중인 이메일입니다."))
                .andDo(MockMvcResultHandlers.print());
        Assertions.assertThat(memberRepository.findByUsername(new Username("test")).get().getEmail()).isEqualTo("test@test.com");
    }

    @Test
    @DisplayName("이메일 형식이 올바르지 않으면, 400에러와 예외문구를 반환하며 이메일은 바뀌지 않는다.")
    public void editEmail_Fail_Format() throws Exception{
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
        EditMemberRequestDto editMemberRequestDto = EditMemberRequestDto.builder()
                .email("newMailtest.com")
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.patch("/api/members/edit")
                .header("Authorization", "Bearer ".concat(tokenResponseDto.getAccessToken()))
                .header("RefreshToken", "Bearer ".concat(tokenResponseDto.getRefreshToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeJson(editMemberRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.failMessage").value("올바르지 않은 이메일 형식입니다."))
                .andDo(MockMvcResultHandlers.print());
        Assertions.assertThat(memberRepository.findByUsername(new Username("test")).get().getEmail()).isEqualTo("test@test.com");
    }

    @Test
    @DisplayName("토큰이 없을 경우, 401에러를 반환한다.")
    public void editFail_No_Token() throws Exception{
        //given
        authService.registerNewMember(RegisterRequestDto.builder()
                .username("test")
                .nickname("test")
                .email("test@test.com").build());
        EditMemberRequestDto editMemberRequestDto = EditMemberRequestDto.builder()
                .build();
        //expected
        mvc.perform(MockMvcRequestBuilders.patch("/api/members/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(makeJson(editMemberRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print());
    }

    private String makeJson(Object object){
        try{
            return new ObjectMapper().writeValueAsString(object);
        }catch (Exception e){
            return "";
        }
    }
}
