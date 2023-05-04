package com.solvd.qaprotours.web.mapper;

import com.solvd.qaprotours.domain.user.Ticket;
import com.solvd.qaprotours.web.dto.user.TicketDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Lisov Ilya
 */
@Mapper(componentModel = "spring")
public interface TicketMapper {

    /**
     * Converts TicketDto to Ticket.
     * @param ticketDto TicketDto object.
     * @return Ticket object.
     */
    Ticket toEntity(TicketDto ticketDto);

    /**
     * Converts Ticket to TicketDto.
     * @param ticket Ticket object.
     * @return TicketDto object.
     */
    TicketDto toDto(Ticket ticket);

    /**
     * Converts List of Ticket to List of TicketDto.
     * @param tickets List of Ticket objects.
     * @return List of TicketDto objects.
     */
    List<TicketDto> toDto(List<Ticket> tickets);

}
