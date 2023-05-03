package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.user.Ticket;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.TicketService;
import com.solvd.qaprotours.service.UserClient;
import com.solvd.qaprotours.web.dto.MailDataDto;
import com.solvd.qaprotours.web.dto.user.UserDto;
import com.solvd.qaprotours.web.kafka.MessageSender;
import com.solvd.qaprotours.web.mapper.MailDataMapper;
import com.solvd.qaprotours.web.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private Scheduler scheduler;

    @Test
    public void findBookedTickets() {
        String userId = "1";
        String userName = "Mike";
        String userSurname = "Ivanov";
        String userEmail = "mike@example.com";
        User user = new User();
        user.setId(userId);
        user.setName(userName);
        user.setSurname(userSurname);
        user.setEmail(userEmail);
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName(userName);
        userDto.setSurname(userSurname);
        userDto.setEmail(userEmail);
        String tourName = "Tour";
        String tourCountry = "Egypt";
        String tourCity = "Cairo";
        LocalDateTime tourArrivalTime = LocalDateTime.now().plusDays(1);
        Tour tour = new Tour();
        tour.setName(tourName);
        tour.setCountry(tourCountry);
        tour.setCity(tourCity);
        tour.setArrivalTime(tourArrivalTime);
        Ticket ticket = new Ticket();
        ticket.setUserId(userId);
        ticket.setTour(tour);
        List<Ticket> tickets = List.of(ticket);
        when(ticketService.getAllSoonTickets())
                .thenReturn(Flux.just(tickets.toArray(new Ticket[0])));
        when(userClient.getById(userId))
                .thenReturn(Mono.just(userDto));
        when(userMapper.toEntity(userDto))
                .thenReturn(user);
        when(mailDataMapper.toDto(any()))
                .thenReturn(new MailDataDto());
        when(messageSender.sendMessage(eq("mail"), anyInt(), anyString(), any()))
                .thenReturn(Flux.empty());
        scheduler.findBookedTickets();
        verify(messageSender, times(tickets.size())).sendMessage(eq("mail"), anyInt(), anyString(), any());
    }

    @Test
    public void findNotConfirmedTickets() {
        String userId = "1";
        String userName = "Mike";
        String userSurname = "Ivanov";
        String userEmail = "mike@example.com";
        User user = new User();
        user.setId(userId);
        user.setName(userName);
        user.setSurname(userSurname);
        user.setEmail(userEmail);
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName(userName);
        userDto.setSurname(userSurname);
        userDto.setEmail(userEmail);
        String tourName = "Tour";
        String tourCountry = "Egypt";
        String tourCity = "Cairo";
        LocalDateTime tourArrivalTime = LocalDateTime.now().plusDays(1);
        Tour tour = new Tour();
        tour.setName(tourName);
        tour.setCountry(tourCountry);
        tour.setCity(tourCity);
        tour.setArrivalTime(tourArrivalTime);
        Ticket ticket = new Ticket();
        ticket.setUserId(userId);
        ticket.setTour(tour);
        List<Ticket> tickets = List.of(ticket);
        when(ticketService.getAllSoonNotConfirmedTickets())
                .thenReturn(Flux.just(tickets.toArray(new Ticket[0])));
        when(userClient.getById(userId))
                .thenReturn(Mono.just(userDto));
        when(userMapper.toEntity(userDto))
                .thenReturn(user);
        when(mailDataMapper.toDto(any()))
                .thenReturn(new MailDataDto());
        when(messageSender.sendMessage(eq("mail"), anyInt(), anyString(), any()))
                .thenReturn(Flux.empty());
        scheduler.findNotConfirmedTickets();
        verify(messageSender, times(tickets.size())).sendMessage(eq("mail"), anyInt(), anyString(), any());
    }

}
