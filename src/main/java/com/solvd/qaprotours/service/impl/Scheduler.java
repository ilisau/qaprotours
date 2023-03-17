package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.MailData;
import com.solvd.qaprotours.domain.MailType;
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

import java.util.HashMap;
import java.util.List;
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
        List<Ticket> tickets = ticketService.getAllSoonTickets();
        tickets.forEach((ticket) -> {
            UserDto userDto = userClient.getById(ticket.getUserId());
            User user = userMapper.toEntity(userDto);
            Map<String, Object> params = new HashMap<>();
            params.put("user.email", user.getEmail());
            params.put("user.name", user.getName());
            params.put("user.surname", user.getSurname());
            params.put("ticket.tour.name", ticket.getTour().getName());
            params.put("ticket.tour.country", ticket.getTour().getCountry());
            params.put("ticket.tour.city", ticket.getTour().getCity());
            params.put("ticket.tour.arrivalTime", ticket.getTour().getArrivalTime());
            MailData mailData = new MailData(MailType.BOOKED_TOUR, params);
            MailDataDto dto = mailDataMapper.toDto(mailData);
            messageSender.sendMessage("mail", 0, user.getId().toString(), dto);
        });
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void findNotConfirmedTickets() {
        List<Ticket> tickets = ticketService.getAllSoonNotConfirmedTickets();
        tickets.forEach((ticket) -> {
            UserDto userDto = userClient.getById(ticket.getUserId());
            User user = userMapper.toEntity(userDto);
            Map<String, Object> params = new HashMap<>();
            params.put("user.email", user.getEmail());
            params.put("user.name", user.getName());
            params.put("user.surname", user.getSurname());
            params.put("ticket.tour.name", ticket.getTour().getName());
            params.put("ticket.tour.country", ticket.getTour().getCountry());
            params.put("ticket.tour.city", ticket.getTour().getCity());
            params.put("ticket.tour.arrivalTime", ticket.getTour().getArrivalTime());
            ticketService.cancel(ticket.getId());
            MailData mailData = new MailData(MailType.TICKET_CANCELED, params);
            MailDataDto dto = mailDataMapper.toDto(mailData);
            messageSender.sendMessage("mail", 0, user.getId().toString(), dto);
        });
    }

}
