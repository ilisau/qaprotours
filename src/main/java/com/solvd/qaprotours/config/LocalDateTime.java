package com.solvd.qaprotours.config;

import graphql.schema.Coercing;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class LocalDateTime implements Coercing<LocalDateTime, String> {

    @Override
    public String serialize(@NotNull Object input) {
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
    public @NotNull LocalDateTime parseValue(@NotNull Object input) {
        return (LocalDateTime) input;
    }

    @Override
    public @NotNull LocalDateTime parseLiteral(@NotNull Object input) {
        return (LocalDateTime) input;
    }

}
