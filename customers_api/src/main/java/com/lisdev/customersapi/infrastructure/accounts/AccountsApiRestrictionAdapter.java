package com.lisdev.customersapi.infrastructure.accounts;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.lisdev.customersapi.domain.Messages;
import com.lisdev.customersapi.application.port.out.AccountRestrictionPort;
import reactor.core.publisher.Mono;

@Component
public class AccountsApiRestrictionAdapter implements AccountRestrictionPort {

    private final WebClient accountsWebClient;

    public AccountsApiRestrictionAdapter(@Qualifier("accountsWebClient") WebClient accountsWebClient) {
        this.accountsWebClient = accountsWebClient;
    }

    @Override
    public Mono<Boolean> existsActiveAccountsForCustomer(Integer customerId) {
        return accountsWebClient.get()
                .uri(Messages.HAS_ACTIVE_ACCOUNTS_PATH, customerId)
                .retrieve()
                .bodyToMono(AccountsExistsResponse.class)
                .map(r -> Boolean.TRUE.equals(r.exists()))
                .defaultIfEmpty(false);
    }

}
