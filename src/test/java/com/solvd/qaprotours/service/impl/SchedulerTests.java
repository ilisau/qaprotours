package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.config.TestConfig;
import com.solvd.qaprotours.web.kafka.MessageSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class SchedulerTests {

    @MockBean
    private MessageSender messageSender;

    @Autowired
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
