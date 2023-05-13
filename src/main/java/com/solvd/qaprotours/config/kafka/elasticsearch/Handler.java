package com.solvd.qaprotours.config.kafka.elasticsearch;

import reactor.kafka.receiver.ReceiverRecord;

public interface Handler {

    /**
     * Handle Kafka message.
     *
     * @param record message
     */
    void handleMessage(ReceiverRecord<String, Object> record);

}
