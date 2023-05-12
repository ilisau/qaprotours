package com.solvd.qaprotours.web.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

/**
 * @param <K> param of a sending object
 * @author Lisov Ilya
 */
@Component
@RequiredArgsConstructor
public class MessageSenderImpl<K> implements MessageSender<K> {

    private final KafkaSender<String, Object> sender;

    @Override
    public Flux<SenderResult<K>> sendMessage(
            final KafkaMessage<K> kafkaMessage
    ) {
        return sender.send(
                Mono.just(
                        SenderRecord.create(
                                kafkaMessage.getTopic(),
                                kafkaMessage.getPartition(),
                                System.currentTimeMillis(),
                                kafkaMessage.getKey(),
                                kafkaMessage.getData(),
                                null
                        )
                )
        );
    }

}
