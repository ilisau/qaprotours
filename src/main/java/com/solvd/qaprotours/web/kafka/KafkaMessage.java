package com.solvd.qaprotours.web.kafka;

import com.solvd.qaprotours.web.dto.MailDataDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KafkaMessage {

    private String topic;
    private int partition;
    private String key;
    private MailDataDto data;

}
