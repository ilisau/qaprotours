package com.solvd.qaprotours.web.mapper;

import com.solvd.qaprotours.domain.user.Ticket;
import com.solvd.qaprotours.web.dto.user.TicketDto;
import org.mapstruct.Mapper;

/**
 * @author Lisov Ilya
 */
@Mapper(componentModel = "spring")
public interface TicketMapper extends Mappable<Ticket, TicketDto> {
}
