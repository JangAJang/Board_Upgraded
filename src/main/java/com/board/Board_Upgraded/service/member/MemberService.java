package com.board.Board_Upgraded.service.member;

import com.board.Board_Upgraded.domain.member.Username;
import com.board.Board_Upgraded.dto.member.*;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
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
    public void editMemberInfo(EditMemberRequestDto editMemberRequestDto, Member member){
        memberInstanceValidator.validateNickname(editMemberRequestDto.getNickname());
        memberInstanceValidator.validateEmail(editMemberRequestDto.getEmail());
        member.changeMemberInfo(editMemberRequestDto.toMemberInfo());
    }

    @Transactional
    public void editMemberPassword(String password, String passwordCheck, Member member){
        memberInstanceValidator.validatePasswordCheck(password, passwordCheck);
        memberInstanceValidator.isPasswordSameWithBefore(member, password);
        member.changePassword(passwordEncoder.encode(password));
    }

    @Transactional(readOnly = true)
    public Page<SearchMemberDto> search(SearchMemberDto searchMemberDto, Pageable pageable){
        return memberRepository.search(searchMemberDto, pageable);
    }

    @Transactional(readOnly = true)
    public SearchMemberDto getMemberInfo(String username){
        Member member = findMemberByUsername(username);
        return SearchMemberDto.builder()
                .username(member.getUsername())
                .nickname(member.getNickname())
                .email(member.getEmail()).build();
    }

    @Transactional
    public String deleteMember(Member member){
        memberRepository.findByUsername(new Username(member.getUsername())).orElseThrow(MemberNotFoundException::new);
        memberRepository.delete(member);
        return "회원이 삭제되었습니다. 그동한 감사합니다.";
    }

    public Member findMemberByUsername(String username){
        return memberRepository.findByUsername(new Username(username)).orElseThrow(MemberNotFoundException::new);
    }
}
