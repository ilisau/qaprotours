package com.solvd.qaprotours.config;

import com.solvd.qaprotours.service.AuthService;
import com.solvd.qaprotours.service.JwtService;
import com.solvd.qaprotours.service.TicketService;
import com.solvd.qaprotours.service.TourService;
import com.solvd.qaprotours.service.UserClient;
import com.solvd.qaprotours.service.impl.fake.FakeAuthService;
import com.solvd.qaprotours.service.impl.fake.FakeJwtService;
import com.solvd.qaprotours.service.impl.fake.FakeTicketService;
import com.solvd.qaprotours.service.impl.fake.FakeTourService;
import com.solvd.qaprotours.service.impl.fake.FakeUserClient;
import com.solvd.qaprotours.service.property.JwtProperties;
import com.solvd.qaprotours.web.mapper.MailDataMapper;
import com.solvd.qaprotours.web.mapper.MailDataMapperImpl;
import com.solvd.qaprotours.web.mapper.UserMapper;
import com.solvd.qaprotours.web.mapper.UserMapperImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
@RequiredArgsConstructor
public class TestConfig {

    private final JwtProperties jwtProperties;

    @Primary
    @Bean
    public UserClient userClient() {
        return new FakeUserClient();
    }

    @Primary
    @Bean
    public TicketService ticketService() {
        return new FakeTicketService();
    }

    @Primary
    @Bean
    public TourService tourService() {
        return new FakeTourService();
    }

    @Primary
    @Bean
    public JwtService jwtService() {
        return new FakeJwtService(jwtProperties);
    }

    @Primary
    @Bean
    public AuthService authService() {
        return new FakeAuthService();
    }

    @Primary
    @Bean
    public MailDataMapper mailDataMapper() {
        return new MailDataMapperImpl();
    }

    @Primary
    @Bean
    public UserMapper userMapper() {
        return new UserMapperImpl();
    }

    @Primary
    @Bean
    public PasswordEncoder testPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
