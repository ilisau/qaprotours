package com.solvd.qaprotours.config;

import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

@Configuration
public class GraphQLConfig {

    /**
     * This bean registers LocalDateTime scalar.
     *
     * @return graphql type
     */
    @Bean
    public GraphQLScalarType localDateTimeScalar() {
        return GraphQLScalarType.newScalar()
                .name("LocalDateTime")
                .description("LocalDateTime scalar")
                .coercing(new Coercing() {
                    @Override
                    public String serialize(final Object input) {
                        SimpleDateFormat formatter
                                = new SimpleDateFormat(
                                "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                                Locale.ENGLISH
                        );
                        return formatter.format(
                                Date.from(((LocalDateTime) input)
                                        .atZone(ZoneId.systemDefault())
                                        .toInstant())
                        );
                    }

                    @Override
                    public Object parseValue(final Object o) {
                        return o;
                    }

                    @Override
                    public Object parseLiteral(final Object o) {
                        return o.toString();
                    }
                })
                .build();
    }

    /**
     * This bean register scalar.
     * @return configuration
     */
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .scalar(localDateTimeScalar());
    }

}

