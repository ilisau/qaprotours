package com.solvd.qaprotours.repository.converters;

import com.solvd.qaprotours.domain.user.Ticket;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.r2dbc.core.Parameter;

@WritingConverter
public class TicketWriteConverter implements Converter<Ticket, OutboundRow> {

    @Override
    public OutboundRow convert(final Ticket ticket) {
        OutboundRow row = new OutboundRow();
        if (ticket.getId() != null) {
            row.put("id", Parameter.from(ticket.getId()));
        }
        if (ticket.getUserId() != null) {
            row.put("user_id", Parameter.from(ticket.getUserId()));
        }
        if (ticket.getTour() != null && ticket.getTour().getId() != null) {
            row.put("tour_id", Parameter.from(ticket.getTour().getId()));
        }
        if (ticket.getClientsAmount() != null) {
            row.put("clients_amount",
                    Parameter.from(ticket.getClientsAmount()));
        }
        if (ticket.getStatus() != null) {
            row.put("status", Parameter.from(ticket.getStatus()));
        }
        if (ticket.getOrderTime() != null) {
            row.put("order_time", Parameter.from(ticket.getOrderTime()));
        }
        return row;
    }

}
