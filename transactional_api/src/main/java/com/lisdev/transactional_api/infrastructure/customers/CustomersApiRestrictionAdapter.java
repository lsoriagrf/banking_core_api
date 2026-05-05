package com.lisdev.transactional_api.infrastructure.customers;

import com.lisdev.transactional_api.application.Messages;
import com.lisdev.transactional_api.application.port.out.CustomerPort;
import com.lisdev.transactional_api.domain.model.CustomerIdentityOutcome;
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

    public CustomersApiRestrictionAdapter(@Qualifier("customersWebClient") WebClient customersWebClient) {
        this.customersWebClient = customersWebClient;
    }

    @Override
    public Mono<Integer> findIdByIdentification(String identification) {
        return get(
                uriBuilder -> uriBuilder
                        .path(Messages.GET_CUSTOMER_ID_BY_IDENTIFICATION)
                        .queryParam("identification", identification)
                        .build(),
                CustomerApiDto.class)
                .map(CustomerApiDto::id)
                .onErrorResume(WebClientResponseException.NotFound.class, ex -> Mono.empty());
    }

    @Override
    public Mono<CustomerIdentityOutcome> resolveCustomerIdentityById(Integer customerId) {
        return get(
                        uriBuilder -> uriBuilder
                                .path(Messages.GET_CUSTOMER_RESOLVE_BY_ID)
                                .queryParam("id", customerId)
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
