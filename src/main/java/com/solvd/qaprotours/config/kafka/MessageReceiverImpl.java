package com.solvd.qaprotours.config.kafka;

import com.solvd.qaprotours.config.kafka.elasticsearch.Handler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.kafka.receiver.KafkaReceiver;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MessageReceiverImpl implements MessageReceiver {

    private final KafkaReceiver<String, Object> receiver;
    private final Map<String, Handler> handlersMap;

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
                    for (Handler handler : handlersMap.values()) {
                        handler.handleMessage(r);
                    }
                });
    }

}
