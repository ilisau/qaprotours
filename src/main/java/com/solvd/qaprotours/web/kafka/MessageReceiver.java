package com.solvd.qaprotours.web.kafka;

public interface MessageReceiver {

    /**
     * Fetches messages from the Kafka topic.
     */
    void fetch();

}
