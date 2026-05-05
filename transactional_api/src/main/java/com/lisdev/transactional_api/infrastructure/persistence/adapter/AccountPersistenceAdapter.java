package com.lisdev.transactional_api.infrastructure.persistence.adapter;

import com.lisdev.transactional_api.application.port.out.AccountPersistencePort;
import com.lisdev.transactional_api.common.PersistenceAdapter;
import com.lisdev.transactional_api.domain.model.Account;
import com.lisdev.transactional_api.infrastructure.persistence.entity.AccountEntity;
import com.lisdev.transactional_api.infrastructure.persistence.mapper.AccountPersistenceMapper;
import com.lisdev.transactional_api.infrastructure.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@PersistenceAdapter
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements AccountPersistencePort {

    private final AccountRepository accountRepository;
    private final AccountPersistenceMapper accountPersistenceMapper;

    @Override
    public Mono<Boolean> existsActiveAccountByCustomerId(Integer customerId) {
        return accountRepository.existsByCustomerIdAndStatusTrue(customerId);
    }

    @Override
    public Flux<Account> findAccountsByCustomerId(Integer customerId) {
        return accountRepository.findByCustomerId(customerId).map(accountPersistenceMapper::toDomain);
    }

    @Override
    public Mono<Account> findActiveAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumberAndStatusTrue(accountNumber).map(accountPersistenceMapper::toDomain);
    }

    @Override
    public Mono<Account> findAccountById(Integer id) {
        return accountRepository.findById(id).map(accountPersistenceMapper::toDomain);
    }

    @Override
    public Mono<Account> save(Account account) {
        AccountEntity entity = accountPersistenceMapper.toEntity(account);
        return accountRepository.save(entity).map(accountPersistenceMapper::toDomain);
    }
}
