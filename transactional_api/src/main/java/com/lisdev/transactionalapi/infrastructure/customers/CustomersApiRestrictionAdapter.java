package com.lisdev.transactionalapi.infrastructure.customers;

import com.lisdev.transactionalapi.application.port.out.CustomerPort;
import com.lisdev.transactionalapi.infrastructure.config.CustomersApiProperties;
import com.lisdev.transactionalapi.domain.model.CustomerIdentityOutcome;
import java.net.URI;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

@Component
public class CustomersApiRestrictionAdapter implements CustomerPort {

    private static final CustomerIdentityOutcome NOT_FOUND = new CustomerIdentityOutcome("", "");

    private final WebClient customersWebClient;
    private final CustomersApiProperties customersApiProperties;

    public CustomersApiRestrictionAdapter(
            @Qualifier("customersWebClient") WebClient customersWebClient,
            CustomersApiProperties customersApiProperties) {
        this.customersWebClient = customersWebClient;
        this.customersApiProperties = customersApiProperties;
    }

    @Override
    public Mono<Integer> findIdByIdentification(String identification) {
        return get(
                uriBuilder -> uriBuilder
                        .path(customersApiProperties.getPath())
                        .pathSegment(identification)
                        .build(),
                CustomerApiDto.class)
                .map(CustomerApiDto::id)
                .onErrorResume(WebClientResponseException.NotFound.class, ex -> Mono.empty());
    }

    @Override
    public Mono<CustomerIdentityOutcome> resolveCustomerIdentityById(Integer customerId) {
        return get(
                        uriBuilder -> uriBuilder
                                .path(customersApiProperties.getPath())
                                .pathSegment(String.valueOf(customerId))
                                .build(),
                        CustomerIdentityApiDto.class)
                .map(dto -> new CustomerIdentityOutcome(dto.identification(), dto.fullName()))
                .defaultIfEmpty(NOT_FOUND)
                .onErrorResume(WebClientResponseException.NotFound.class, ex -> Mono.just(NOT_FOUND));
    }

    private <T> Mono<T> get(Function<UriBuilder, URI> uriFunction, Class<T> responseType) {
        return customersWebClient
                .get()
                .uri(uriFunction)
                .retrieve()
                .bodyToMono(responseType);
    }
}
