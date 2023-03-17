package com.solvd.qaprotours.web.kafka;

import com.solvd.qaprotours.web.dto.MailDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

/**
 * @author Lisov Ilya
 */
@Component
@RequiredArgsConstructor
public class MessageSenderImpl implements MessageSender {

    private final KafkaSender<String, Object> sender;

    @Override
    public void sendMessage(String topic, int partition, String key, MailDataDto data) {
        sender.send(
                        Mono.just(
                                SenderRecord.create(
                                        topic,
                                        partition,
                                        System.currentTimeMillis(),
                                        key,
                                        data,
                                        null
                                )
                        )
                )
                .subscribe();
    }

}
