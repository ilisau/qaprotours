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

    Ticket toEntity(TicketDto ticketDto);

    TicketDto toDto(Ticket ticket);

    List<TicketDto> toDto(List<Ticket> tickets);

}
