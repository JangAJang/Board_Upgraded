package com.board.Board_Upgraded.service.member;

import com.board.Board_Upgraded.dto.member.*;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
import com.board.Board_Upgraded.exception.member.NeedToAddEditConditionException;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Slf4j
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

    @Transactional
    public void editMember(EditMemberRequestDto editMemberRequestDto, Member member){
        if(editMemberRequestDto.getNickname() == null && editMemberRequestDto.getEmail() == null
                && editMemberRequestDto.getPassword() == null)
            throw new NeedToAddEditConditionException();
        if(editMemberRequestDto.getNickname() != null)
            changeMemberNickname(new ChangeNicknameRequestDto(editMemberRequestDto.getNickname()), member);
        if(editMemberRequestDto.getEmail() != null)
            changeMemberEmail(new ChangeEmailRequestDto(editMemberRequestDto.getEmail()), member);
        if(editMemberRequestDto.getPassword() != null && editMemberRequestDto.getPasswordCheck() != null)
            changeMemberPassword(new ChangePasswordRequestDto(editMemberRequestDto.getPassword()
                    , editMemberRequestDto.getPasswordCheck()), member);
    }

    @Transactional(readOnly = true)
    public Page<SearchMemberDto> search(SearchMemberDto searchMemberDto, Pageable pageable){
        return memberRepository.search(searchMemberDto, pageable);
    }

    @Transactional
    public String deleteMember(Member member){
        memberRepository.findByUsername(member.getUsername()).orElseThrow(MemberNotFoundException::new);
        memberRepository.delete(member);
        return "회원이 삭제되었습니다. 그동한 감사합니다.";
    }

    public Member findMemberByUsername(String username){
        return memberRepository.findByUsername(username).orElseThrow(MemberNotFoundException::new);
    }
}
