package com.solvd.qaprotours.web.kafka;

import com.jcabi.xml.XML;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lisov Ilya
 */
@Configuration
public class KfProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;

    private final XML settings;

    @Autowired
    public KfProducerConfig(@Qualifier(value = "producer") XML settings) {
        this.settings = settings;
    }

    /**
     * Create a new topic for users.
     *
     * @return topic
     */
    @Bean
    public NewTopic topicUsers() {
        return TopicBuilder.name("users")
                .partitions(5)
                .replicas(1)
                .build();
    }

    /**
     * Create a new topic for mail.
     * @return topic
     */
    @Bean
    public NewTopic topicMail() {
        return TopicBuilder.name("mail")
                .partitions(5)
                .replicas(1)
                .build();
    }

    /**
     * Create a new topic for ElasticSearch.
     *
     * @return topic
     */
    @Bean
    public NewTopic topicTour() {
        return TopicBuilder.name("tours")
                .partitions(5)
                .replicas(1)
                .build();
    }

    /**
     * Create a kafka admin object.
     *
     * @return kafka admin
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        return new KafkaAdmin(configs);
    }

    /**
     * Create a kafka sender options.
     * @return sender options
     */
    @Bean
    public SenderOptions<String, Object> senderOptions() {
        Map<String, Object> props = new HashMap<>(3);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(
                "key.serializer",
                new TextXpath(this.settings, "//keySerializer")
                        .toString()
        );
        props.put(
                "value.serializer",
                new TextXpath(this.settings, "//valueSerializer")
                        .toString()
        );
        return SenderOptions.create(props);
    }

    /**
     * Create a kafka sender.
     * @param senderOptions sender options
     * @return kafka sender
     */
    @Bean
    public KafkaSender<String, Object> sender(
            final SenderOptions<String, Object> senderOptions
    ) {
        return KafkaSender.create(senderOptions);
    }

}
