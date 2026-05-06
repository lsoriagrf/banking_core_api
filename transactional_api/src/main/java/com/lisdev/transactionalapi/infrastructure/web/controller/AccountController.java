package com.lisdev.transactionalapi.infrastructure.web.controller;

import com.lisdev.transactionalapi.application.dto.response.AccountResponse;
import com.lisdev.transactionalapi.application.port.in.AccountPortIn;
import com.lisdev.transactionalapi.common.WebAdapter;
import com.lisdev.transactionalapi.infrastructure.web.dto.request.CreateAccount;
import com.lisdev.transactionalapi.infrastructure.web.dto.response.ExistsResponse;
import com.lisdev.transactionalapi.infrastructure.web.mapper.AccountWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import reactor.core.publisher.Mono;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountPortIn accountPortIn;
    private final AccountWebMapper accountWebMapper;

    @PostMapping
    public Mono<ResponseEntity<AccountResponse>> createAccount(@Valid @RequestBody CreateAccount body) {
        return accountPortIn
                .createAccount(accountWebMapper.toCreateCommand(body))
                .map(accountWebMapper::toResponse)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @GetMapping("/{identification}")
    public Mono<ResponseEntity<List<AccountResponse>>> findAccountsByIdentification(
            @PathVariable String identification) {
        return accountPortIn
                .findAccountsByIdentification(identification)
                .map(accountWebMapper::toResponse)
                .collectList()
                .map(accountResponses -> ResponseEntity.status(HttpStatus.OK).body(accountResponses));
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<AccountResponse>> updateAccountStatus(
            @PathVariable Integer id, @RequestParam Boolean status) {
        return accountPortIn
                .updateAccountStatus(id, status)
                .map(accountWebMapper::toResponse)
                .map(response -> ResponseEntity.status(HttpStatus.OK).body(response));
    }

    @GetMapping("/exists")
    public Mono<ResponseEntity<ExistsResponse>> existsAccountByCustomerId(@RequestParam Integer customerId) {
        return accountPortIn
                .existsAccountByCustomerId(customerId)
                .map(ExistsResponse::new)
                .map(ResponseEntity::ok);
    }

}
