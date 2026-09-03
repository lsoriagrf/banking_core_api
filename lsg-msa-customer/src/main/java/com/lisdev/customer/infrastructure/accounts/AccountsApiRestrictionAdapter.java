package com.lisdev.customer.infrastructure.accounts;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.lisdev.customer.application.port.out.AccountRestrictionPort;
import com.lisdev.customer.infrastructure.config.AccountsApiProperties;
import reactor.core.publisher.Mono;

@Component
public class AccountsApiRestrictionAdapter implements AccountRestrictionPort {

    private final WebClient accountsWebClient;
    private final AccountsApiProperties accountsApiProperties;

    public AccountsApiRestrictionAdapter(@Qualifier("accountsWebClient") WebClient accountsWebClient,
            AccountsApiProperties accountsApiProperties) {
        this.accountsWebClient = accountsWebClient;
        this.accountsApiProperties = accountsApiProperties;
    }

    @Override
    public Mono<Boolean> existsActiveAccountsForCustomer(Integer customerId) {
        return accountsWebClient.get()
                .uri(accountsApiProperties.getExistsActiveAccountsPath(), customerId)
                .retrieve()
                .bodyToMono(AccountsExistsResponse.class)
                .map(r -> Boolean.TRUE.equals(r.exists()))
                .defaultIfEmpty(false);
    }

}
