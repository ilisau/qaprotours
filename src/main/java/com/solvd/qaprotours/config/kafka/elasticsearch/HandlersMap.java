package com.solvd.qaprotours.config.kafka.elasticsearch;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.kafka.receiver.ReceiverRecord;

@Component
@RequiredArgsConstructor
@Data
public class HandlersMap implements Handler {

    private final IndexHandler indexHandler;
    private final DeleteHandler deleteHandler;

    @Override
    public void handleMessage(final ReceiverRecord<String, Object> record) {
        indexHandler.handleMessage(record);
        deleteHandler.handleMessage(record);
    }

}
