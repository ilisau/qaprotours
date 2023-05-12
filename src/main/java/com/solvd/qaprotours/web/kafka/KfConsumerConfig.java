package com.solvd.qaprotours.web.kafka;

import com.jcabi.xml.XML;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lisov Ilya
 */
@Configuration
public class KfConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;

    private final XML settings;

    /**
     * Constructor.
     *
     * @param settings consumer settings
     */
    @Autowired
    public KfConsumerConfig(@Qualifier(value = "consumer") final XML settings) {
        this.settings = settings;
    }

    /**
     * Creates receiver options.
     *
     * @return receiver options
     */
    @Bean
    public ReceiverOptions<String, Object> receiverOptions() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(
                "group.id",
                new TextXpath(
                        this.settings, "//groupId"
                ).toString()
        );
        props.put(
                "key.deserializer",
                new TextXpath(
                        this.settings, "//keyDeserializer"
                ).toString()
        );
        props.put(
                "value.deserializer",
                new TextXpath(
                        this.settings, "//valueDeserializer"
                ).toString()
        );
        props.put(
                "spring.json.trusted.packages",
                new TextXpath(
                        this.settings, "//trustedPackages"
                ).toString()
        );
        ReceiverOptions<String, Object> receiverOptions = ReceiverOptions
                .create(props);
        return receiverOptions.subscription(Collections.singleton("tours"))
                .addAssignListener(partitions ->
                        System.out.println("onPartitionAssigned: "
                                + partitions))
                .addRevokeListener(partitions ->
                        System.out.println("onPartitionRevoked: "
                                + partitions));
    }

    /**
     * Creates receiver.
     *
     * @param receiverOptions receiver options
     * @return receiver
     */
    @Bean
    public KafkaReceiver<String, Object> receiver(
            final ReceiverOptions<String, Object> receiverOptions
    ) {
        return KafkaReceiver.create(receiverOptions);
    }

}
