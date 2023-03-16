package com.solvd.qaprotours.web.kafka;

import com.solvd.qaprotours.web.dto.MailDataDto;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.SenderResult;

/**
 * @author Lisov Ilya
 */
public interface MessageSender {

    Flux<SenderResult<MailDataDto>> sendMessage(String topic, int partition, String key, MailDataDto data);

}
