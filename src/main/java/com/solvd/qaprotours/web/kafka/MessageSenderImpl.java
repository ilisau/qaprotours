package com.solvd.qaprotours.web.kafka;

import com.solvd.qaprotours.web.dto.MailDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

/**
 * @author Lisov Ilya
 */
@Component
@RequiredArgsConstructor
public class MessageSenderImpl implements MessageSender {

    private final KafkaSender<String, Object> sender;

    @Override
    public Flux<SenderResult<MailDataDto>> sendMessage(
            final KafkaMessage kafkaMessage
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
