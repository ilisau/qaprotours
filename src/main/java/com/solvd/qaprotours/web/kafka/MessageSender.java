package com.solvd.qaprotours.web.kafka;

import com.solvd.qaprotours.web.dto.MailDataDto;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.SenderResult;

/**
 * @author Lisov Ilya
 */
public interface MessageSender {

    /**
     * Send message to kafka.
     *
     * @param kafkaMessage message to send
     * @return sender result
     */
    Flux<SenderResult<MailDataDto>> sendMessage(KafkaMessage kafkaMessage);

}
