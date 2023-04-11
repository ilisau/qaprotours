package com.solvd.qaprotours.web.controller;

import com.solvd.qaprotours.service.TicketService;
import com.solvd.qaprotours.web.dto.user.TicketDto;
import com.solvd.qaprotours.web.mapper.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
    public Mono<TicketDto> getTicket(@PathVariable Long ticketId) {
        return ticketService.getById(ticketId)
                .map(ticketMapper::toDto);
    }

    @DeleteMapping("/{ticketId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("canAccessTicket(#ticketId)")
    public Mono<Void> deleteTicket(@PathVariable Long ticketId) {
        return ticketService.delete(ticketId);
    }

    @PostMapping("/{ticketId}/confirm")
    @PreAuthorize("canConfirmTicket()")
    public Mono<Void> confirmTicket(@PathVariable Long ticketId) {
        return ticketService.confirm(ticketId);
    }

}
