package com.lisdev.account.infrastructure.messaging;

import com.lisdev.account.application.port.in.CustomerEventHandlerPortIn;
import com.lisdev.account.common.MessagingAdapter;
import com.lisdev.account.domain.event.CustomerEvent;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.util.retry.Retry;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
@MessagingAdapter
@RequiredArgsConstructor
public class CustomerEventKafkaConsumer {

    private final KafkaReceiver<String, String> customerEventsReceiver;
    private final CustomerEventHandlerPortIn customerEventHandler;
    private final JsonMapper jsonMapper;

    private Disposable subscription;

    @PostConstruct
    void start() {
        subscription = customerEventsReceiver.receive()
                .concatMap(this::process)
                .retryWhen(Retry.backoff(Long.MAX_VALUE, Duration.ofSeconds(5)))
                .subscribe();
    }

    @PreDestroy
    void stop() {
        if (subscription != null) {
            subscription.dispose();
        }
    }

    private Mono<Void> process(ReceiverRecord<String, String> record) {
        return Mono.fromCallable(() -> jsonMapper.readValue(record.value(), CustomerEvent.class))
                .flatMap(customerEventHandler::handle)
                .onErrorResume(error -> {
                    log.error("Discarding customer event at offset {}: {}",
                            record.offset(), record.value(), error);
                    return Mono.empty();
                })
                .doFinally(signal -> record.receiverOffset().acknowledge());
    }

}
