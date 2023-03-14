package com.board.Board_Upgraded.config.auth;

import com.board.Board_Upgraded.domain.member.Username;
import com.board.Board_Upgraded.entity.member.Member;
import com.board.Board_Upgraded.exception.member.MemberNotFoundException;
import com.board.Board_Upgraded.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(new Username(username))
                .map(this::createUserDetails)
                .orElseThrow(MemberNotFoundException::new);
    }

    private UserDetails createUserDetails(Member member){
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getRole().toString());
        return new org.springframework.security.core.userdetails.User(
                member.getUsername(),
                member.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}

