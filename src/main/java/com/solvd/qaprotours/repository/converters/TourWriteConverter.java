package com.solvd.qaprotours.repository.converters;

import com.solvd.qaprotours.domain.tour.Tour;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.r2dbc.core.Parameter;

@WritingConverter
public class TourWriteConverter implements Converter<Tour, OutboundRow> {

    @Override
    public OutboundRow convert(final Tour tour) {
        OutboundRow row = new OutboundRow();
        if (tour.getId() != null) {
            row.put("id", Parameter.from(tour.getId()));
        }
        if (tour.getName() != null) {
            row.put("name", Parameter.from(tour.getName()));
        }
        if (tour.getDescription() != null) {
            row.put("description", Parameter.from(tour.getDescription()));
        }
        if (tour.getArrivalTime() != null) {
            row.put("arrival_time", Parameter.from(tour.getArrivalTime()));
        }
        if (tour.getDepartureTime() != null) {
            row.put("departure_time", Parameter.from(tour.getDepartureTime()));
        }
        if (tour.getPlacesAmount() != null) {
            row.put("place_amount", Parameter.from(tour.getPlacesAmount()));
        }
        if (tour.getPrice() != null) {
            row.put("price", Parameter.from(tour.getPrice()));
        }
        if (tour.getCountry() != null) {
            row.put("country", Parameter.from(tour.getCountry()));
        }
        if (tour.getCity() != null) {
            row.put("city", Parameter.from(tour.getCity()));
        }
        if (tour.getType() != null) {
            row.put("type", Parameter.from(tour.getType()));
        }
        if (tour.getCateringType() != null) {
            row.put("catering_type", Parameter.from(tour.getCateringType()));
        }
        if (!tour.isDraft()) {
            row.put("is_draft", Parameter.from(tour.isDraft()));
        }
        if (tour.getRating() != null) {
            row.put("rating", Parameter.from(tour.getRating()));
        }
        if (tour.getDayDuration() != null) {
            row.put("day_duration", Parameter.from(tour.getDayDuration()));
        }
        if (tour.getCoordinates() != null
                && tour.getCoordinates().getLatitude() != null) {
            row.put("latitude",
                    Parameter.from(tour.getCoordinates().getLatitude()));
        }
        if (tour.getCoordinates() != null
                && tour.getCoordinates().getLongitude() != null) {
            row.put("longitude",
                    Parameter.from(tour.getCoordinates().getLongitude()));
        }
        if (tour.getHotel() != null && tour.getHotel().getId() != null) {
            row.put("hotel_id", Parameter.from(tour.getHotel().getId()));
        }
        //TODO save image_urls
        return row;
    }

}
