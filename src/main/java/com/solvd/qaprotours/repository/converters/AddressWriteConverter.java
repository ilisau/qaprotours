package com.solvd.qaprotours.repository.converters;

import com.solvd.qaprotours.domain.hotel.Address;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.r2dbc.core.Parameter;

@WritingConverter
public class AddressWriteConverter implements Converter<Address, OutboundRow> {

    @Override
    public OutboundRow convert(final Address address) {
        OutboundRow row = new OutboundRow();
        if (address.getId() != null) {
            row.put("id", Parameter.from(address.getId()));
        }
        if (address.getCity() != null) {
            row.put("city", Parameter.from(address.getCity()));
        }
        if (address.getCountry() != null) {
            row.put("country", Parameter.from(address.getCountry()));
        }
        if (address.getStreetName() != null) {
            row.put("street", Parameter.from(address.getStreetName()));
        }
        if (address.getHouseNumber() != null) {
            row.put("house_number", Parameter.from(address.getHouseNumber()));
        }
        return row;
    }

}
