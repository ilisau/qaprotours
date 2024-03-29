package com.solvd.qaprotours.config.kafka;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KafkaMessage<K> {

    private String topic;
    private int partition;
    private String key;
    private K data;

}
