package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.MailData;
import com.solvd.qaprotours.domain.MailType;
import com.solvd.qaprotours.service.TicketService;
import com.solvd.qaprotours.service.UserClient;
import com.solvd.qaprotours.web.dto.MailDataDto;
import com.solvd.qaprotours.web.kafka.MessageSender;
import com.solvd.qaprotours.web.mapper.MailDataMapper;
import com.solvd.qaprotours.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lisov Ilya
 */
@Service
@RequiredArgsConstructor
public class Scheduler {

    private final TicketService ticketService;
    private final UserClient userClient;
    private final MailDataMapper mailDataMapper;
    private final UserMapper userMapper;
    private final MessageSender messageSender;

    @Scheduled(cron = "0 0 0 * * *")
    public void findBookedTickets() {
        ticketService.getAllSoonTickets()
                .collectList()
                .map(tickets -> {
                    tickets.forEach(ticket ->
                            userClient.getById(ticket.getUserId())
                                    .map(userMapper::toEntity)
                                    .map(user -> {
                                        Map<String, Object> params =
                                                new HashMap<>();
                                        params.put("user.email",
                                                user.getEmail());
                                        params.put("user.name",
                                                user.getName());
                                        params.put("user.surname",
                                                user.getSurname());
                                        params.put("ticket.tour.name",
                                                ticket.getTour().getName());
                                        params.put("ticket.tour.country",
                                                ticket.getTour().getCountry());
                                        params.put("ticket.tour.city",
                                                ticket.getTour().getCity());
                                        params.put("ticket.tour.arrivalTime",
                                                ticket.getTour()
                                                        .getArrivalTime());
                                        return params;
                                    })
                                    .flatMap(params -> {
                                        MailData mailData =
                                                new MailData(MailType.BOOKED_TOUR,
                                                        params);
                                        MailDataDto dto =
                                                mailDataMapper.toDto(mailData);
                                        return messageSender.sendMessage(
                                                        "mail",
                                                        0,
                                                        ticket.getUserId(),
                                                        dto)
                                                .then();
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
                    tickets.forEach(ticket ->
                            userClient.getById(ticket.getUserId())
                                    .map(userMapper::toEntity)
                                    .map(user -> {
                                        Map<String, Object> params = new HashMap<>();
                                        params.put("user.email",
                                                user.getEmail());
                                        params.put("user.name",
                                                user.getName());
                                        params.put("user.surname",
                                                user.getSurname());
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
                                MailData mailData = new MailData(
                                        MailType.TICKET_CANCELED,
                                        params);
                                MailDataDto dto =
                                        mailDataMapper.toDto(mailData);
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

}
