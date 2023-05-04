package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.user.Ticket;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ReadingConverter
public class ReadConverter implements Converter<Row, Ticket> {

    @Override
    public Ticket convert(final Row source) {
        Ticket ticket = new Ticket();
        ticket.setId(source.get("id", Long.class));
        ticket.setUserId(source.get("user_id", String.class));
        ticket.setOrderTime(source.get("order_time", LocalDateTime.class));
        ticket.setStatus(Ticket.Status
                .valueOf(source.get("status", String.class)));
        ticket.setClientsAmount(source.get("clients_amount",
                Integer.class));
        Tour tour = new Tour();
        tour.setId(source.get("tour_id", Long.class));
        tour.setName(source.get("name", String.class));
        tour.setDescription(source.get("description", String.class));
        tour.setArrivalTime(source.get("arrival_time", LocalDateTime.class));
        tour.setDepartureTime(source.get("departure_time",
                LocalDateTime.class));
        tour.setPlacesAmount(source.get("places_amount", Integer.class));
        tour.setPrice(source.get("price", BigDecimal.class));
        tour.setCountry(source.get("country", String.class));
        tour.setCity(source.get("city", String.class));
        tour.setType(Tour.TourType
                .valueOf(source.get("type", String.class)));
        tour.setCateringType(Tour.CateringType
                .valueOf(source.get("catering_type", String.class)));
        tour.setDraft(source.get("is_draft", Boolean.class));
        tour.setRating(source.get("rating", BigDecimal.class));
        tour.setDayDuration(source.get("day_duration", Integer.class));
        ticket.setTour(tour);
        return ticket;
    }

}
