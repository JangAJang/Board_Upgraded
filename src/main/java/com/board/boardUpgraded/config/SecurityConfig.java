package com.board.boardUpgraded.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(auth->
                        auth.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().anyRequest().requestMatchers("/swagger-ui");
    }
}
