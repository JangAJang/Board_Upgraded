package com.board.Board_Upgraded.config.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        log.error("Unauthorized request : time : " + now + "\nrequest : " + request + "\nException : " + authException.getMessage());
        response.sendError(401, "다시 로그인해주세요.");
    }
}
