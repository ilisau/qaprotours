package com.solvd.qaprotours.repository;

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
     * Custom converter for {@link TicketRepository}.
     *
     * @return custom converter
     */
    @Bean
    public R2dbcCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new TicketReadConverter());
        return R2dbcCustomConversions.of(MySqlDialect.INSTANCE, converters);
    }

}
