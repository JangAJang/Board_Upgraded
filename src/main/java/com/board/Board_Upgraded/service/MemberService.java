package com.board.Board_Upgraded.service;

import com.board.Board_Upgraded.dto.member.ChangeEmailRequestDto;
import com.board.Board_Upgraded.dto.member.RegisterRequestDto;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.EmailAlreadyInUseException;
import com.board.Board_Upgraded.exception.member.NicknameAlreadyInUseException;
import com.board.Board_Upgraded.exception.member.UsernameAlreadyInUseException;
import com.board.Board_Upgraded.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void registerNewMember(RegisterRequestDto registerRequestDto){
        validateUsername(registerRequestDto.getUsername());
        validateNickname(registerRequestDto.getNickname());
        validateEmail(registerRequestDto.getEmail());
        registerRequestDto.setPassword(bCryptPasswordEncoder.encode(registerRequestDto.getPassword()));
        registerRequestDto.setPasswordCheck(bCryptPasswordEncoder.encode(registerRequestDto.getPasswordCheck()));
        Member member = new Member(registerRequestDto);
        memberRepository.save(member);
    }

    private void validateUsername(String username){
        if(memberRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyInUseException();
    }

    private void validateNickname(String nickname){
        if(memberRepository.findByNickname(nickname).isPresent())
            throw new NicknameAlreadyInUseException();
    }

    private void validateEmail(String email){
        if(memberRepository.findByEmail(email).isPresent())
            throw new EmailAlreadyInUseException();
    }

    @Transactional
    public void changeMemberEmail(ChangeEmailRequestDto changeEmailRequestDto, Member member){
        validateEmail(changeEmailRequestDto.getNewEmail());
        member.setEmail(changeEmailRequestDto.getNewEmail());
        memberRepository.save(member);
    }
}
