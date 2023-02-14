package com.board.Board_Upgraded.service;

import com.board.Board_Upgraded.dto.member.*;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import com.board.Board_Upgraded.repository.member.SearchType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private MemberInstanceValidator memberInstanceValidator;

    @PostConstruct
    private void setValidator(){
        this.memberInstanceValidator = new MemberInstanceValidator(memberRepository, passwordEncoder);
    }

    @Transactional
    public void changeMemberEmail(ChangeEmailRequestDto changeEmailRequestDto, Member member){
        memberInstanceValidator.validateEmail(changeEmailRequestDto.getNewEmail());
        member.changeEmail(changeEmailRequestDto);
    }

    @Transactional
    public void changeMemberNickname(ChangeNicknameRequestDto changeNicknameRequestDto, Member member){
        memberInstanceValidator.validateNickname(changeNicknameRequestDto.getNewNickname());
        member.changeNickname(changeNicknameRequestDto);
    }

    @Transactional
    public void changeMemberPassword(ChangePasswordRequestDto changePasswordRequestDto, Member member){
        memberInstanceValidator.validatePasswordCheck(changePasswordRequestDto.getNewPassword(), changePasswordRequestDto.getNewPasswordCheck());
        memberInstanceValidator.validateWithCurrentPassword(changePasswordRequestDto, member.getPassword());
        changePasswordRequestDto.setNewPasswordCheck(passwordEncoder.encode(changePasswordRequestDto.getNewPassword()));
        member.changePassword(changePasswordRequestDto);
    }

    @Transactional(readOnly = true)
    public Page<SearchMemberDto> search(SearchMemberDto searchMemberDto, Pageable pageable, SearchType searchType){
        return memberRepository.search(searchMemberDto, pageable, searchType);
    }
}
