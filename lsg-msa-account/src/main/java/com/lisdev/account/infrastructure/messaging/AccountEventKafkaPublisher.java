package com.lisdev.account.infrastructure.messaging;

import com.lisdev.account.application.port.out.AccountEventPublisherPort;
import com.lisdev.account.common.MessagingAdapter;
import com.lisdev.account.domain.event.AccountEvent;
import com.lisdev.account.infrastructure.config.KafkaProperties;
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
public class AccountEventKafkaPublisher implements AccountEventPublisherPort {

    private final KafkaSender<String, String> kafkaSender;
    private final KafkaProperties kafkaProperties;
    private final JsonMapper jsonMapper;

    @Override
    public Mono<Void> publish(AccountEvent event) {
        return Mono.fromCallable(() -> toRecord(event))
                .flatMap(record -> kafkaSender.send(Mono.just(record)).then())
                .doOnSuccess(ignored -> log.info("Published {} eventId={} accountId={}",
                        event.eventType(), event.eventId(), event.payload().accountId()))
                .doOnError(error -> log.error("Failed to publish {} eventId={}",
                        event.eventType(), event.eventId(), error));
    }

    private SenderRecord<String, String, String> toRecord(AccountEvent event) {
        ProducerRecord<String, String> record = new ProducerRecord<>(
                kafkaProperties.getTopics().getAccountEvents(),
                partitionKey(event),
                jsonMapper.writeValueAsString(event));
        return SenderRecord.create(record, event.eventId().toString());
    }

    private String partitionKey(AccountEvent event) {
        return String.valueOf(event.payload().customerId());
    }

}
