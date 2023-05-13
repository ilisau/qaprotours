package com.solvd.qaprotours.config.kafka;

import com.solvd.qaprotours.config.kafka.elasticsearch.HandlersMap;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.kafka.receiver.KafkaReceiver;

@Component
@RequiredArgsConstructor
public class MessageReceiverImpl implements MessageReceiver {

    private final KafkaReceiver<String, Object> receiver;
    private final HandlersMap handlersMap;

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
                .subscribe(handlersMap::handleMessage);
    }

}
