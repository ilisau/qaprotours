package com.solvd.qaprotours.web.kafka;

import reactor.core.publisher.Flux;
import reactor.kafka.sender.SenderResult;

/**
 * @author Lisov Ilya
 */
public interface MessageSender<K> {

    /**
     * Send message to kafka.
     *
     * @param kafkaMessage message to send
     * @return sender result
     */
    Flux<SenderResult<K>> sendMessage(KafkaMessage<K> kafkaMessage);

}
