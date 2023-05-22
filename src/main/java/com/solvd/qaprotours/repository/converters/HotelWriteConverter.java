package com.solvd.qaprotours.repository.converters;

import com.solvd.qaprotours.domain.hotel.Hotel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.r2dbc.core.Parameter;

@WritingConverter
public class HotelWriteConverter implements Converter<Hotel, OutboundRow> {

    @Override
    public OutboundRow convert(final Hotel hotel) {
        OutboundRow row = new OutboundRow();
        if (hotel.getId() != null) {
            row.put("id", Parameter.from(hotel.getId()));
        }
        if (hotel.getCoastline() != null) {
            row.put("coastline", Parameter.from(hotel.getCoastline()));
        }
        if (hotel.getName() != null) {
            row.put("name", Parameter.from(hotel.getName()));
        }
        if (hotel.getStarsAmount() != null) {
            row.put("stars_amount", Parameter.from(hotel.getStarsAmount()));
        }
        if (hotel.getAddress() != null && hotel.getAddress().getId() != null) {
            row.put("address_id", Parameter.from(hotel.getAddress().getId()));
        }
        return row;
    }

}
