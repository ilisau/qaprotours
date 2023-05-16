package com.solvd.qaprotours.config;

import com.solvd.qaprotours.repository.TicketRepository;
import com.solvd.qaprotours.repository.TourRepository;
import com.solvd.qaprotours.service.AuthService;
import com.solvd.qaprotours.service.JwtService;
import com.solvd.qaprotours.service.TicketService;
import com.solvd.qaprotours.service.TourService;
import com.solvd.qaprotours.service.UserClient;
import com.solvd.qaprotours.service.impl.AuthServiceImpl;
import com.solvd.qaprotours.service.impl.ImageServiceImpl;
import com.solvd.qaprotours.service.impl.Scheduler;
import com.solvd.qaprotours.service.impl.TicketServiceImpl;
import com.solvd.qaprotours.service.impl.TourServiceImpl;
import com.solvd.qaprotours.service.impl.fake.FakeAuthService;
import com.solvd.qaprotours.service.impl.fake.FakeJwtService;
import com.solvd.qaprotours.service.impl.fake.FakeTicketService;
import com.solvd.qaprotours.service.impl.fake.FakeTourService;
import com.solvd.qaprotours.service.impl.fake.FakeUserClient;
import com.solvd.qaprotours.service.property.ImageProperties;
import com.solvd.qaprotours.service.property.JwtProperties;
import com.solvd.qaprotours.service.property.MinioProperties;
import com.solvd.qaprotours.web.kafka.MessageSender;
import com.solvd.qaprotours.web.mapper.MailDataMapper;
import com.solvd.qaprotours.web.mapper.MailDataMapperImpl;
import com.solvd.qaprotours.web.mapper.UserMapper;
import com.solvd.qaprotours.web.mapper.UserMapperImpl;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

@TestConfiguration
@EnableR2dbcRepositories
@RequiredArgsConstructor
public class TestConfig {

    private final TicketRepository ticketRepository;
    private final TourRepository tourRepository;

    @Bean
    public JwtProperties jwtProperties() {
        return new JwtProperties(
                1,
                24,
                1,
                1,
                "c651749e8354adb09452a8ad14d4beadb7d57064d6cfb5c0e812bc30724458d187f43e58c22c30486303bbfc655391860952e92add87b5ce9e2ac7cdc612ad73=");
    }

    @Bean("ticketServiceImpl")
    public TicketServiceImpl ticketServiceImpl() {
        return new TicketServiceImpl(ticketRepository, tourService());
    }

    @Bean("imageServiceImpl")
    public ImageServiceImpl imageServiceImpl() {
        return new ImageServiceImpl(minioClient(), minioProperties(), tourService(), imageProperties());
    }

    @Bean
    public MinioClient minioClient() {
        return null;
    }

    @Bean
    public MinioProperties minioProperties() {
        return new MinioProperties("tours", "", "", "");
    }

    @Bean
    public ImageProperties imageProperties() {
        return new ImageProperties(Collections.emptyList(), Collections.emptyList());
    }

    @Bean("authServiceImpl")
    public AuthServiceImpl authServiceImpl() {
        return new AuthServiceImpl(userClient(), testPasswordEncoder(), jwtService(), mailDataMapper(), userMapper(), messageSender());
    }

    @Bean("tourServiceImpl")
    public TourServiceImpl tourServiceImpl() {
        return new TourServiceImpl(tourRepository);
    }

    @Primary
    @Bean
    public Scheduler scheduler() {
        return new Scheduler(ticketService(), userClient(), mailDataMapper(), userMapper(), messageSender());
    }

    @Primary
    @Bean
    public MessageSender messageSender() {
        return null;
    }

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
    @Bean(name = "fakeTourService")
    public TourService tourService() {
        return new FakeTourService();
    }

    @Primary
    @Bean
    public JwtService jwtService() {
        return new FakeJwtService(jwtProperties());
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
    public BCryptPasswordEncoder testPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
