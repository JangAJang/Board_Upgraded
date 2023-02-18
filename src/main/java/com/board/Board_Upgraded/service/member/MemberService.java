package com.board.Board_Upgraded.service.member;

import com.board.Board_Upgraded.dto.member.*;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
import com.board.Board_Upgraded.exception.member.NeedToAddEditConditionException;
import com.board.Board_Upgraded.exception.member.NeedToPutPasswordTwiceToEditException;
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
    public void editMember(EditMemberRequestDto editMemberRequestDto, Member member){
        if(editMemberRequestDto.getNickname() == null && editMemberRequestDto.getEmail() == null
                && editMemberRequestDto.getPassword() == null && editMemberRequestDto.getPasswordCheck() == null)
            throw new NeedToAddEditConditionException();
        if((editMemberRequestDto.getPassword() != null && editMemberRequestDto.getPasswordCheck() == null) ||
                (editMemberRequestDto.getPassword() == null && editMemberRequestDto.getPasswordCheck() != null))
            throw new NeedToPutPasswordTwiceToEditException();
        if(editMemberRequestDto.getNickname() != null)
            changeMemberNickname(editMemberRequestDto.getNickname(), member);
        if(editMemberRequestDto.getEmail() != null)
            changeMemberEmail(editMemberRequestDto.getEmail(), member);
        if(editMemberRequestDto.getPassword() != null && editMemberRequestDto.getPasswordCheck() != null)
            changeMemberPassword(editMemberRequestDto.getPassword()
                    , editMemberRequestDto.getPasswordCheck(), member);
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

    private void changeMemberEmail(String email, Member member){
        memberInstanceValidator.validateEmail(email);
        member.changeEmail(email);
    }

    private void changeMemberNickname(String nickname, Member member){
        memberInstanceValidator.validateNickname(nickname);
        member.changeNickname(nickname);
    }

    private void changeMemberPassword(String password, String passwordCheck, Member member){
        memberInstanceValidator.validatePasswordCheck(password, passwordCheck);
        memberInstanceValidator.validateWithCurrentPassword(password, member.getPassword());
        member.changePassword(passwordEncoder.encode(password));
    }

    public Member findMemberByUsername(String username){
        return memberRepository.findByUsername(username).orElseThrow(MemberNotFoundException::new);
    }
}
