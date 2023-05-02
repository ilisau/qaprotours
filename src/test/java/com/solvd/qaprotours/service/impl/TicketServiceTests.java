package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.user.Ticket;
import com.solvd.qaprotours.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTests {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    void getByExistingId() {
        Long id = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(id);

        when(ticketRepository.findById(id))
                .thenReturn(Mono.justOrEmpty(Optional.of(ticket)));

        assertEquals(ticket, ticketService.getById(id).block());
        verify(ticketRepository).findById(id);
    }

}
