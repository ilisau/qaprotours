package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.service.TicketService;
import com.solvd.qaprotours.service.UserClient;
import com.solvd.qaprotours.service.impl.fake.FakeScheduler;
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

@ExtendWith(MockitoExtension.class)
public class SchedulerTests {

    @Mock
    private TicketService ticketService;

    @Mock
    private UserClient userClient;

    @Mock
    private UserMapper userMapper;

    @Mock
    private MailDataMapper mailDataMapper;

    @Mock
    private MessageSender messageSender;

    @InjectMocks
    private FakeScheduler scheduler;

    @Test
    void findBookedTickets() {
        scheduler.findBookedTickets();
        Mockito.verify(messageSender)
                .sendMessage(ArgumentMatchers.eq("mail"),
                        ArgumentMatchers.anyInt(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any());
    }

//    @Test
//    void findNotConfirmedTickets() {
//        List<Ticket> tickets = generateTickets(user, tour);
//        Mockito.when(ticketService.getAllSoonNotConfirmedTickets())
//                .thenReturn(Flux.just(tickets.toArray(new Ticket[0])));
//        Mockito.when(userClient.getById(user.getId()))
//                .thenReturn(Mono.just(userDto));
//        Mockito.when(userMapper.toEntity(userDto))
//                .thenReturn(user);
//        Mockito.when(mailDataMapper.toDto(ArgumentMatchers.any()))
//                .thenReturn(new MailDataDto());
//        Mockito.when(messageSender
//                        .sendMessage(ArgumentMatchers.eq("mail"),
//                                ArgumentMatchers.anyInt(),
//                                ArgumentMatchers.anyString(),
//                                ArgumentMatchers.any())
//                )
//                .thenReturn(Flux.empty());
//        scheduler.findNotConfirmedTickets();
//        Mockito.verify(messageSender, Mockito.times(tickets.size()))
//                .sendMessage(ArgumentMatchers.eq("mail"),
//                        ArgumentMatchers.anyInt(),
//                        ArgumentMatchers.anyString(),
//                        ArgumentMatchers.any());
//    }

}
