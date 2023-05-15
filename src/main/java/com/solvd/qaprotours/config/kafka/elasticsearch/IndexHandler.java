package com.solvd.qaprotours.config.kafka.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.solvd.qaprotours.config.kafka.LocalDateTimeDeserializer;
import com.solvd.qaprotours.web.dto.TourDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.ReceiverRecord;

import java.io.StringReader;
import java.time.LocalDateTime;

@Service("indexHandler")
@Slf4j
@RequiredArgsConstructor
public class IndexHandler implements Handler {

    private final ElasticsearchClient client;
    private final LocalDateTimeDeserializer localDateTimeDeserializer;

    @Override
    public void handleMessage(final ReceiverRecord<String, Object> r) {
        if (r.value() != null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class,
                            localDateTimeDeserializer)
                    .create();
            TourDto tour = gson
                    .fromJson(r.value().toString(), TourDto.class);
            IndexRequest.Builder<TourDto> builder
                    = new IndexRequest.Builder<>();
            IndexRequest<TourDto> indexRequest = builder
                    .id(tour.getId().toString())
                    .index("tours")
                    .withJson(new StringReader(r.value().toString()))
                    .build();
            try {
                client.index(indexRequest);
                r.receiverOffset().acknowledge();
            } catch (Exception e) {
                log.warn("Request is not sent because of exception: "
                        + e.getMessage());
            }
        }
    }

}
