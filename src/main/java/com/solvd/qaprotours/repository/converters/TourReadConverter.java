package com.solvd.qaprotours.repository.converters;

import com.solvd.qaprotours.domain.hotel.Address;
import com.solvd.qaprotours.domain.hotel.Hotel;
import com.solvd.qaprotours.domain.hotel.Point;
import com.solvd.qaprotours.domain.tour.Tour;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ReadingConverter
public class TourReadConverter implements Converter<Row, Tour> {

    @Override
    public Tour convert(final Row source) {
        Tour tour = new Tour();
        tour.setId(source.get("id", Long.class));
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
        Point point = new Point();
        point.setLatitude(source.get("latitude", Double.class));
        point.setLongitude(source.get("longitude", Double.class));
        tour.setCoordinates(point);
        Hotel hotel = new Hotel();
        hotel.setId(source.get("hotel_id", Long.class));
        hotel.setName(source.get("hotel_name", String.class));
        hotel.setCoastline(source.get("coastline", Integer.class));
        hotel.setStarsAmount(source.get("stars_amount", Integer.class));
        Address address = new Address();
        address.setId(source.get("address_id", Long.class));
        address.setCity(source.get("address_city", String.class));
        address.setCountry(source.get("address_country", String.class));
        address.setStreetName(source.get("street", String.class));
        address.setHouseNumber(source.get("house_number", Integer.class));
        hotel.setAddress(address);
        tour.setHotel(hotel);
        //TODO set image url
        return tour;
    }

}
