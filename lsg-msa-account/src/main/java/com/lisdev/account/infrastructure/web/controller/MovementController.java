package com.lisdev.account.infrastructure.web.controller;

import com.lisdev.account.application.port.in.MovementPortIn;
import com.lisdev.account.common.WebAdapter;
import com.lisdev.account.infrastructure.web.dto.request.Transaction;
import com.lisdev.account.infrastructure.web.dto.response.TransactionResponse;
import com.lisdev.account.infrastructure.web.mapper.MovementWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movements")
public class MovementController {

    private final MovementPortIn movementPortIn;
    private final MovementWebMapper movementWebMapper;

    @PostMapping(path = "/withdrawal")
    public Mono<TransactionResponse> withdrawal(@Valid @RequestBody Transaction body) {
        return movementPortIn
                .withdrawal(movementWebMapper.toTransactionCommand(body))
                .map(movementWebMapper::toTransactionResponse);
    }

    @PostMapping(path = "/deposit")
    public Mono<TransactionResponse> deposit(@Valid @RequestBody Transaction body) {
        return movementPortIn
                .deposit(movementWebMapper.toTransactionCommand(body))
                .map(movementWebMapper::toTransactionResponse);
    }
}
