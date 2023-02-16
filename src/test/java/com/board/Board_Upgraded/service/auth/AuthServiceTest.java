package com.board.Board_Upgraded.service.auth;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.dto.member.SignInRequestDto;
import com.board.Board_Upgraded.dto.token.ReissueRequestDto;
import com.board.Board_Upgraded.dto.token.TokenResponseDto;
import com.board.Board_Upgraded.exception.member.EmailAlreadyInUseException;
import com.board.Board_Upgraded.exception.member.NicknameAlreadyInUseException;
import com.board.Board_Upgraded.exception.member.PasswordNotMatchingException;
import com.board.Board_Upgraded.exception.member.UsernameAlreadyInUseException;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void initData(){
        memberRepository.deleteAll();
        for(int index = 1; index <=10; index++){
            RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                    .username("testUser" + index)
                    .nickname("test" + index)
                    .email("test"  + index + "@test.com")
                    .password("테스트" + index)
                    .passwordCheck("테스트" + index)
                    .build();
            authService.registerNewMember(registerRequestDto);
            System.out.println("test data activated");
        }
    }

    @Test
    @DisplayName("회원가입_아이디 중복")
    public void 아이디_중복() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("testUser1")
                .nickname("아장아장")
                .email("jangajang@email.com")
                .nickname("아장아장")
                .password("아장아장")
                .passwordCheck("아장아장")
                .build();
        //when

        //then
        assertThatThrownBy(()-> authService.registerNewMember(registerRequestDto))
                .isInstanceOf(UsernameAlreadyInUseException.class);
    }
    
    @Test
    @DisplayName("회원가입_닉네임 중복")
    public void 닉네임_중복() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("jangajang")
                .nickname("test1")
                .email("jangajang@email.com")
                .password("아장아장")
                .passwordCheck("아장아장")
                .build();
        //when

        //then
        assertThatThrownBy(()-> authService.registerNewMember(registerRequestDto))
                .isInstanceOf(NicknameAlreadyInUseException.class);
    }
    
    @Test
    @DisplayName("회원가입_이메일 중복")
    public void 이메일_중복() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("jangajang")
                .nickname("아장아장")
                .email("test1@test.com")
                .nickname("아장아장")
                .password("아장아장")
                .passwordCheck("아장아장")
                .build();
        //when

        //then
        assertThatThrownBy(()-> authService.registerNewMember(registerRequestDto))
                .isInstanceOf(EmailAlreadyInUseException.class);
    }
    
    @Test
    @DisplayName("회원가입_비밀번호 불일치")
    public void 비밀번호_불일치() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("jangajang")
                .nickname("아장아장")
                .email("jangajang@email.com")
                .nickname("아장아장")
                .password("아장아장")
                .passwordCheck("아장아장1")
                .build();
        //when

        //then
        assertThatThrownBy(()-> authService.registerNewMember(registerRequestDto))
                .isInstanceOf(PasswordNotMatchingException.class);
    }
    
    @Test
    @DisplayName("회원가입_정상동작")
    public void 회원가입_정상동작() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("jangajang")
                .nickname("아장아장")
                .email("jangajang@test.com")
                .nickname("아장아장")
                .password("아장아장")
                .passwordCheck("아장아장")
                .build();
        //when

        //then
        authService.registerNewMember(registerRequestDto);
    }

    @Test
    @DisplayName("로그인시에 Access Token, Refresh Token을 출력")
    public void 로그인_출력() throws Exception{
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("jangajang")
                .nickname("아장아장")
                .email("jangajang@test.com")
                .nickname("아장아장")
                .password("아장아장")
                .passwordCheck("아장아장")
                .build();
        authService.registerNewMember(registerRequestDto);
        //when
        SignInRequestDto signInRequestDto = SignInRequestDto.builder()
                .username("jangajang")
                .password("아장아장").build();
        //then
        TokenResponseDto tokenResponseDto = authService.signIn(signInRequestDto);
        System.out.println("Access Token : " + tokenResponseDto.getAccessToken());
        System.out.println("Refresh Token : " + tokenResponseDto.getRefreshToken());
    }

    @Test
    @DisplayName("토큰 재발행을 실행할 때, 재발행된 토큰의 값이 생성되어 반환되어야 한다. ")
    public void reissueTest() throws Exception{
        //given
        TokenResponseDto tokenResponseDto = authService.signIn(
                SignInRequestDto.builder().username("testUser1").password("테스트1").build());
        //when
        ReissueRequestDto reissueRequestDto = new ReissueRequestDto(tokenResponseDto.getRefreshToken(), tokenResponseDto.getAccessToken());
        //then
        TokenResponseDto reissuedToken = authService.reissue(reissueRequestDto);
        assertThat(reissuedToken).isNotNull();
    }
}
