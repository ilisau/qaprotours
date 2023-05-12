package com.solvd.qaprotours.web.kafka;

import com.solvd.qaprotours.web.kafka.elasticsearch.DeleteHandler;
import com.solvd.qaprotours.web.kafka.elasticsearch.IndexHandler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.kafka.receiver.KafkaReceiver;

@Component
@RequiredArgsConstructor
public class MessageReceiverImpl implements MessageReceiver {

    private final KafkaReceiver<String, Object> receiver;
    private final IndexHandler indexHandler;
    private final DeleteHandler deleteHandler;

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
                    indexHandler.handleMessage(r);
                    deleteHandler.handleMessage(r);
                });
    }

}
