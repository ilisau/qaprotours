package com.solvd.qaprotours.repository.converters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.MySqlDialect;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RepositoryConfig {

    /**
     * Custom converter for repositories.
     *
     * @return custom converters
     */
    @Bean
    public R2dbcCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new TicketReadConverter());
        converters.add(new TicketWriteConverter());
        converters.add(new TourReadConverter());
        converters.add(new TourWriteConverter());
        converters.add(new HotelWriteConverter());
        converters.add(new AddressWriteConverter());
        return R2dbcCustomConversions.of(MySqlDialect.INSTANCE, converters);
    }

}
