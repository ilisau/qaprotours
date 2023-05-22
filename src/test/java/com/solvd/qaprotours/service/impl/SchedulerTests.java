package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.config.TestConfig;
import com.solvd.qaprotours.config.kafka.MessageSender;
import com.solvd.qaprotours.repository.TicketRepository;
import com.solvd.qaprotours.repository.TourRepository;
import com.solvd.qaprotours.service.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.SenderResult;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class SchedulerTests {

    @MockBean
    private MessageSender messageSender;

    @Mock
    @Qualifier("ticketService")
    private TicketService ticketService;

    @MockBean
    private TicketRepository ticketRepository;

    @MockBean
    private TourRepository tourRepository;

    @Autowired
    private Scheduler scheduler;

    @Test
    void findBookedTickets() {
        scheduler.findBookedTickets();
        Mockito.when(messageSender.sendMessage(ArgumentMatchers.any()))
                .thenReturn(Flux.empty());
        Mockito.verify(messageSender)
                .sendMessage(ArgumentMatchers.any());
    }

    @Test
    void findNotConfirmedTickets() {
        scheduler.findNotConfirmedTickets();
        Mockito.when(messageSender.sendMessage(ArgumentMatchers.any()))
                .thenReturn(Flux.just(Mockito.mock(SenderResult.class)));
        Mockito.verify(messageSender)
                .sendMessage(ArgumentMatchers.any());
    }

}