package com.lisdev.customer.infrastructure.messaging;

import com.lisdev.customer.application.port.out.CustomerEventPublisherPort;
import com.lisdev.customer.common.MessagingAdapter;
import com.lisdev.customer.domain.event.CustomerEvent;
import com.lisdev.customer.infrastructure.config.KafkaProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
@MessagingAdapter
@RequiredArgsConstructor
public class CustomerEventKafkaPublisher implements CustomerEventPublisherPort {

    private final KafkaSender<String, String> kafkaSender;
    private final KafkaProperties kafkaProperties;
    private final JsonMapper jsonMapper;

    @Override
    public Mono<Void> publish(CustomerEvent event) {
        return Mono.fromCallable(() -> toRecord(event))
                .flatMap(record -> kafkaSender.send(Mono.just(record)).then())
                .doOnSuccess(ignored -> log.info("Published {} eventId={} customerId={}",
                        event.eventType(), event.eventId(), event.payload().customerId()))
                .doOnError(error -> log.error("Failed to publish {} eventId={}",
                        event.eventType(), event.eventId(), error));
    }

    private SenderRecord<String, String, String> toRecord(CustomerEvent event) {
        ProducerRecord<String, String> record = new ProducerRecord<>(
                kafkaProperties.getTopics().getCustomerEvents(),
                partitionKey(event),
                jsonMapper.writeValueAsString(event));
        return SenderRecord.create(record, event.eventId().toString());
    }

    private String partitionKey(CustomerEvent event) {
        return String.valueOf(event.payload().customerId());
    }

}
