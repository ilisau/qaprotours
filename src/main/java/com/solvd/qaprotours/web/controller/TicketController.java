package com.solvd.qaprotours.web.controller;

import com.solvd.qaprotours.domain.user.Ticket;
import com.solvd.qaprotours.service.TicketService;
import com.solvd.qaprotours.web.dto.user.TicketDto;
import com.solvd.qaprotours.web.mapper.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Lisov Ilya
 */
@RestController
@RequestMapping("api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @GetMapping("/{ticketId}")
    @PreAuthorize("canAccessTicket(#ticketId)")
    public TicketDto getTicket(@PathVariable Long ticketId) {
        Ticket ticket = ticketService.getById(ticketId);
        return ticketMapper.toDto(ticket);
    }

    @DeleteMapping("/{ticketId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("canAccessTicket(#ticketId)")
    public void deleteTicket(@PathVariable Long ticketId) {
        ticketService.delete(ticketId);
    }

    @PostMapping("/{ticketId}/confirm")
    @PreAuthorize("canConfirmTicket()")
    public void confirmTicket(@PathVariable Long ticketId) {
        ticketService.confirm(ticketId);
    }

}
