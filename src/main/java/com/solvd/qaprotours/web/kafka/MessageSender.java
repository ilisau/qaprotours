package com.solvd.qaprotours.web.kafka;

/**
 * @author Lisov Ilya
 */
public interface MessageSender {

    void sendMessage(String topic, int partition, Object data);

}
