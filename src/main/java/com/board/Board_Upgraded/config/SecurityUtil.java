package com.board.Board_Upgraded.config;

import com.board.Board_Upgraded.exception.authentication.NeedToLoginException;
import com.board.Board_Upgraded.exception.authentication.NotRightAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {

    public static Long getCurrentMemberId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) throw new NeedToLoginException();
        if(authentication.getName() == null) throw new NotRightAuthenticationException();
        return Long.parseLong(authentication.getName());
    }
}
