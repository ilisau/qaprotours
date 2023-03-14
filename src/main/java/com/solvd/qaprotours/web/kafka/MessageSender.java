package com.solvd.qaprotours.web.kafka;

import com.solvd.qaprotours.web.dto.MailDataDto;

/**
 * @author Lisov Ilya
 */
public interface MessageSender {

    void sendMessage(String topic, int partition, String key, MailDataDto data);

}
