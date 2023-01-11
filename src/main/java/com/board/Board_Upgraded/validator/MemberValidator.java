package com.board.Board_Upgraded.validator;

import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.exception.member.EmailAlreadyInUseException;
import com.board.Board_Upgraded.exception.member.EmailNotFormatException;
import com.board.Board_Upgraded.exception.member.NicknameAlreadyInUseException;
import com.board.Board_Upgraded.exception.member.UsernameAlreadyInUseException;
import com.board.Board_Upgraded.repository.MemberRepository;

import java.util.regex.Pattern;

public class MemberValidator {

    private MemberRepository memberRepository;

    public void validate(RegisterRequestDto registerRequestDto){
        checkException(registerRequestDto);
    }

    private void checkException(RegisterRequestDto registerRequestDto){
        validateUsername(registerRequestDto.getUsername());
        validateNickname(registerRequestDto.getNickname());
        validateEmail(registerRequestDto.getEmail());
    }

    public void validateUsername(String username){
        if(memberRepository.findByUsername(username).isEmpty()) return;
        throw new UsernameAlreadyInUseException();
    }

    public void validateNickname(String nickname){
        if(memberRepository.findByNickname(nickname).isEmpty()) return;
        throw new NicknameAlreadyInUseException();
    }

    public void validateEmail(String email){
        validateEmailFormat(email);
        validateEmailExistence(email);
    }

    public void validateEmailFormat(String email){
        if(!Pattern.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", email))
            throw new EmailNotFormatException();
    }

    private void validateEmailExistence(String email){
        if(memberRepository.findByEmail(email).isEmpty()) return;
        throw new EmailAlreadyInUseException();
    }
}
