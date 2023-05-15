package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.config.TestConfig;
import com.solvd.qaprotours.service.TicketService;
import com.solvd.qaprotours.service.UserClient;
import com.solvd.qaprotours.web.kafka.MessageSender;
import com.solvd.qaprotours.web.mapper.MailDataMapper;
import com.solvd.qaprotours.web.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class SchedulerTests {

    @Mock
    private UserClient userClient;

    @Mock
    private TicketService ticketService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private MailDataMapper mailDataMapper;

    @Mock
    private MessageSender messageSender;

    @InjectMocks
    private Scheduler scheduler;

    @Test
    void findBookedTickets() {
        scheduler.findBookedTickets();
        Mockito.verify(messageSender)
                .sendMessage(ArgumentMatchers.eq("mail"),
                        ArgumentMatchers.anyInt(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any());
    }

    @Test
    void findNotConfirmedTickets() {
        scheduler.findNotConfirmedTickets();
        Mockito.verify(messageSender)
                .sendMessage(ArgumentMatchers.eq("mail"),
                        ArgumentMatchers.anyInt(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any());
    }

}
