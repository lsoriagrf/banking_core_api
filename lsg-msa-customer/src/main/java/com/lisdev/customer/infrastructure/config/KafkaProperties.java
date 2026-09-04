package com.lisdev.customer.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.kafka")
public class KafkaProperties {

    private String bootstrapServers;
    private String consumerGroupId;
    private Topics topics = new Topics();

    @Getter
    @Setter
    public static class Topics {

        private String customerEvents;
        private String accountEvents;
    }

}
