package com.solvd.qaprotours.config.kafka;

public interface MessageReceiver {

    /**
     * Fetches messages from the Kafka topic.
     */
    void fetch();

}
