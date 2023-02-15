package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.MailType;
import com.solvd.qaprotours.domain.user.Ticket;
import com.solvd.qaprotours.service.MailService;
import com.solvd.qaprotours.service.TicketService;
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

    private final MailService mailService;
    private final TicketService ticketService;

    @Scheduled(cron = "0 0 0 * * *")
    public void findBookedTickets() {
        List<Ticket> tickets = ticketService.getAllSoonTickets();
        tickets.forEach((ticket) -> {
            Map<String, Object> params = new HashMap<>();
            params.put("ticket", ticket);
            mailService.sendMail(ticket.getUser(), MailType.BOOKED_TOUR, params);
        });
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void findNotConfirmedTickets() {
        List<Ticket> tickets = ticketService.getAllSoonNotConfirmedTickets();
        tickets.forEach((ticket) -> {
            Map<String, Object> params = new HashMap<>();
            params.put("ticket", ticket);
            ticketService.cancel(ticket.getId());

            mailService.sendMail(ticket.getUser(), MailType.TICKET_CANCELED, params);
        });
    }

}
