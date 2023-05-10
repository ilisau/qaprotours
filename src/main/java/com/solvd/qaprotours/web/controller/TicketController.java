package com.solvd.qaprotours.web.controller;

import com.solvd.qaprotours.service.TicketService;
import com.solvd.qaprotours.web.dto.user.TicketDto;
import com.solvd.qaprotours.web.mapper.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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

    /**
     * Get ticket by id.
     * @param ticketId ticket id
     * @return ticket
     */
    @GetMapping("/{ticketId}")
    @PreAuthorize("canAccessTicket(#ticketId)")
    public Mono<TicketDto> getTicket(@PathVariable final Long ticketId) {
        return ticketService.getById(ticketId)
                .map(ticketMapper::toDto);
    }

    /**
     * Delete a ticket by id.
     * @param ticketId ticket id
     * @return empty response
     */
    @DeleteMapping("/{ticketId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("canAccessTicket(#ticketId)")
    public Mono<Void> deleteTicket(@PathVariable final Long ticketId) {
        return ticketService.delete(ticketId);
    }

    /**
     * Confirm a ticket by id.
     * @param ticketId ticket id
     * @return empty response
     */
    @PostMapping("/{ticketId}/confirm")
    @PreAuthorize("canConfirmTicket()")
    public Mono<Void> confirmTicket(@PathVariable final Long ticketId) {
        return ticketService.confirm(ticketId);
    }

}
