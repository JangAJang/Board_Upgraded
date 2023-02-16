package com.board.Board_Upgraded.config;

import com.board.Board_Upgraded.config.jwt.JwtAccessDenialHandler;
import com.board.Board_Upgraded.config.jwt.JwtAuthenticationEntryPoint;
import com.board.Board_Upgraded.config.jwt.JwtSecurityConfig;
import com.board.Board_Upgraded.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDenialHandler jwtAccessDenialHandler;
    private final CorsConfig config;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/api/auth/sign_in", "/api/auth/join");
    }

    @Bean
    public PasswordEncoder encode(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.httpBasic(withDefaults())
                .authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll();

        http.addFilter(config.corsFilter())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable();

        http.exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDenialHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/reissue").access("hasAuthority('USER') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
                .antMatchers("/api/members/**").access("hasAuthority('USER') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
        return http.build();
    }
}
