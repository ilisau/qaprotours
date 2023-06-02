package com.solvd.qaprotours.web.controller;

import com.solvd.qaprotours.service.TicketService;
import com.solvd.qaprotours.web.dto.user.TicketDto;
import com.solvd.qaprotours.web.mapper.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
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
     *
     * @param id ticket id
     * @return ticket
     */
    @GetMapping("/{id}")
    @QueryMapping
    @PreAuthorize("canAccessTicket(#id)")
    public Mono<TicketDto> ticketById(@PathVariable @Argument final Long id) {
        return ticketService.getById(id)
                .map(ticketMapper::toDto);
    }

    /**
     * Delete a ticket by id.
     *
     * @param id ticket id
     * @return empty response
     */
    @DeleteMapping("/{id}")
    @MutationMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("canAccessTicket(#id)")
    public Mono<Void> deleteTicket(@PathVariable @Argument final Long id) {
        return ticketService.delete(id);
    }

    /**
     * Confirm a ticket by id.
     *
     * @param id ticket id
     * @return empty response
     */
    @PostMapping("/{id}/confirm")
    @MutationMapping
    @PreAuthorize("canConfirmTicket()")
    public Mono<Void> confirmTicket(@PathVariable @Argument final Long id) {
        return ticketService.confirm(id);
    }

}
