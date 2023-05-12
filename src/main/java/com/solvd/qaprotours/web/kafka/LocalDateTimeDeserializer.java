package com.solvd.qaprotours.web.kafka;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class LocalDateTimeDeserializer
        implements JsonDeserializer<LocalDateTime> {

    private final DateTimeFormatter dateTimeFormatter;

    @Override
    public LocalDateTime deserialize(final JsonElement json,
                                     final Type typeOfT,
                                     final JsonDeserializationContext context) {
        String dateTimeString = json.getAsString();
        return LocalDateTime.parse(dateTimeString, dateTimeFormatter);
    }

}

