package com.solvd.qaprotours.config;

import graphql.schema.Coercing;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class LocalDateTimeCoercing implements Coercing<LocalDateTimeCoercing,
        String> {

    @Override
    public String serialize(final @NotNull Object input) {
        SimpleDateFormat formatter
                = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                Locale.ENGLISH
        );
        return formatter.format(
                Date.from(((java.time.LocalDateTime) input)
                        .atZone(ZoneId.systemDefault())
                        .toInstant())
        );
    }

    @Override
    public @NotNull LocalDateTimeCoercing parseValue(
            final @NotNull Object input
    ) {
        return (LocalDateTimeCoercing) input;
    }

    @Override
    public @NotNull LocalDateTimeCoercing parseLiteral(
            final @NotNull Object input
    ) {
        return (LocalDateTimeCoercing) input;
    }

}
