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
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

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
    void findBookedTickets() {
        User user = generateUser();
        UserDto userDto = generateUserDto();
        Tour tour = generateTour();
        List<Ticket> tickets = generateTickets(user, tour);
        Mockito.when(ticketService.getAllSoonTickets())
                .thenReturn(Flux.just(tickets.toArray(new Ticket[0])));
        Mockito.when(userClient.getById(user.getId()))
                .thenReturn(Mono.just(userDto));
        Mockito.when(userMapper.toEntity(userDto))
                .thenReturn(user);
        Mockito.when(mailDataMapper.toDto(ArgumentMatchers.any()))
                .thenReturn(new MailDataDto());
        Mockito.when(messageSender
                        .sendMessage(ArgumentMatchers.eq("mail"),
                                ArgumentMatchers.anyInt(),
                                ArgumentMatchers.anyString(),
                                ArgumentMatchers.any())
                )
                .thenReturn(Flux.empty());
        scheduler.findBookedTickets();
        Mockito.verify(messageSender, Mockito.times(tickets.size()))
                .sendMessage(ArgumentMatchers.eq("mail"),
                        ArgumentMatchers.anyInt(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any());
    }

    @Test
    void findNotConfirmedTickets() {
        User user = generateUser();
        UserDto userDto = generateUserDto();
        Tour tour = generateTour();
        List<Ticket> tickets = generateTickets(user, tour);
        Mockito.when(ticketService.getAllSoonNotConfirmedTickets())
                .thenReturn(Flux.just(tickets.toArray(new Ticket[0])));
        Mockito.when(userClient.getById(user.getId()))
                .thenReturn(Mono.just(userDto));
        Mockito.when(userMapper.toEntity(userDto))
                .thenReturn(user);
        Mockito.when(mailDataMapper.toDto(ArgumentMatchers.any()))
                .thenReturn(new MailDataDto());
        Mockito.when(messageSender
                        .sendMessage(ArgumentMatchers.eq("mail"),
                                ArgumentMatchers.anyInt(),
                                ArgumentMatchers.anyString(),
                                ArgumentMatchers.any())
                )
                .thenReturn(Flux.empty());
        scheduler.findNotConfirmedTickets();
        Mockito.verify(messageSender, Mockito.times(tickets.size()))
                .sendMessage(ArgumentMatchers.eq("mail"),
                        ArgumentMatchers.anyInt(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any());
    }

    private User generateUser() {
        String userId = "1";
        String userName = "Mike";
        String userSurname = "Ivanov";
        String userEmail = "mike@example.com";
        User user = new User();
        user.setId(userId);
        user.setName(userName);
        user.setSurname(userSurname);
        user.setEmail(userEmail);
        return user;
    }

    private UserDto generateUserDto() {
        String userId = "1";
        String userName = "Mike";
        String userSurname = "Ivanov";
        String userEmail = "mike@example.com";
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName(userName);
        userDto.setSurname(userSurname);
        userDto.setEmail(userEmail);
        return userDto;
    }

    private Tour generateTour() {
        String tourName = "Tour";
        String tourCountry = "Egypt";
        String tourCity = "Cairo";
        LocalDateTime tourArrivalTime = LocalDateTime.now().plusDays(1);
        Tour tour = new Tour();
        tour.setName(tourName);
        tour.setCountry(tourCountry);
        tour.setCity(tourCity);
        tour.setArrivalTime(tourArrivalTime);
        return tour;
    }

    private List<Ticket> generateTickets(User user, Tour tour) {
        Ticket ticket = new Ticket();
        ticket.setUserId(user.getId());
        ticket.setTour(tour);
        List<Ticket> tickets = List.of(ticket);
        return tickets;
    }

}
