package com.solvd.qaprotours.web.kafka;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.solvd.qaprotours.web.dto.TourDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.kafka.receiver.KafkaReceiver;

@Component
@RequiredArgsConstructor
public class MessageReceiverImpl implements MessageReceiver {

    private final KafkaReceiver<String, Object> receiver;
    private final ElasticsearchClient client;

    /**
     * Initialize receiver.
     */
    @PostConstruct
    public void init() {
        fetch();
    }

    @Override
    public void fetch() {
        receiver.receive()
                .subscribe(r -> {
                    KafkaMessage<TourDto> json =
                            (KafkaMessage<TourDto>) r.value();
                    //TODO push to Elastic here
                    r.receiverOffset().acknowledge();
                });
    }

}
