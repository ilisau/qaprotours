package com.solvd.qaprotours.web.security;

import com.solvd.qaprotours.web.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;

/**
 * @author Ermakovich Kseniya
 */
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity( prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;
    private static final String[] permitPostAll;
    public static final String SECURITY_SCHEME_NAME = "bearerAuth";


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, permitPostAll).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(jwtFilter, ExceptionTranslationFilter.class)
                .build();
    }

    static {
        permitPostAll = new String[] {
                "/api/v1/users/login",
                "/api/v1/users/refresh"
        };
    }

}
