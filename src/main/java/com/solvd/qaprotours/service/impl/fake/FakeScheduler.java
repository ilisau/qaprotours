package com.solvd.qaprotours.service.impl.fake;

import com.solvd.qaprotours.domain.MailData;
import com.solvd.qaprotours.domain.MailType;
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
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lisov Ilya
 */
@Service
@RequiredArgsConstructor
public class FakeScheduler {

    private final TicketService ticketService;
    private final UserClient userClient;
    private final MailDataMapper mailDataMapper;
    private final UserMapper userMapper;
    private final MessageSender messageSender;

    @Scheduled(cron = "0 0 0 * * *")
    public void findBookedTickets() {
        User generatedUser = generateUser();
        UserDto generatedUserDto = generateUserDto();
        Tour generatedTour = generateTour();
        List<Ticket> ticketList = generateTickets(generatedUser, generatedTour);
        Flux.just(ticketList)
                .map(tickets -> {
                    tickets.forEach(ticket -> Mono.just(generatedUserDto)
                            .map(dto -> generatedUser)
                            .map(user -> {
                                Map<String, Object> params = new HashMap<>();
                                params.put("user.email", user.getEmail());
                                params.put("user.name", user.getName());
                                params.put("user.surname", user.getSurname());
                                params.put("ticket.tour.name",
                                        ticket.getTour().getName());
                                params.put("ticket.tour.country",
                                        ticket.getTour().getCountry());
                                params.put("ticket.tour.city",
                                        ticket.getTour().getCity());
                                params.put("ticket.tour.arrivalTime",
                                        ticket.getTour().getArrivalTime());
                                return params;
                            })
                            .flatMap(params -> {
                                MailData mailData =
                                        new MailData(
                                                MailType.BOOKED_TOUR,
                                                params
                                        );
                                MailDataDto dto =
                                        new MailDataDto();
                                messageSender.sendMessage(
                                        "mail",
                                        0,
                                        ticket.getUserId(),
                                        dto
                                );
                                return Mono.empty();
                            })
                            .subscribe());
                    return tickets;
                })
                .subscribe();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void findNotConfirmedTickets() {
        ticketService.getAllSoonNotConfirmedTickets()
                .collectList()
                .map(tickets -> {
                    tickets.forEach(ticket -> userClient.getById(ticket.getUserId())
                            .map(userMapper::toEntity)
                            .map(user -> {
                                Map<String, Object> params = new HashMap<>();
                                params.put("user.email", user.getEmail());
                                params.put("user.name", user.getName());
                                params.put("user.surname", user.getSurname());
                                params.put("ticket.tour.name", ticket.getTour().getName());
                                params.put("ticket.tour.country", ticket.getTour().getCountry());
                                params.put("ticket.tour.city", ticket.getTour().getCity());
                                params.put("ticket.tour.arrivalTime", ticket.getTour().getArrivalTime());
                                return params;
                            })
                            .flatMap(params -> {
                                MailData mailData = new MailData(MailType.TICKET_CANCELED, params);
                                MailDataDto dto = mailDataMapper.toDto(mailData);
                                return messageSender.sendMessage("mail",
                                        0,
                                        ticket.getUserId(),
                                        dto
                                ).then();
                            })
                            .flatMap(t -> ticketService.cancel(ticket.getId()))
                            .subscribe());
                    return tickets;
                })
                .subscribe();
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
