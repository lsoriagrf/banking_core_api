package com.lisdev.customers_api.infrastructure.web.controller;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lisdev.customers_api.common.WebAdapter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@WebAdapter
@RestController
@RequestMapping("/api/v1/isalive")
public class IsAliveController {

    @GetMapping
    public Mono<Map<String, Object>> isAlive() {
        return Mono.<Map<String, Object>>just(Map.of(
                "status", "UP",
                "timestamp", LocalDateTime.now().toString()
        )).doOnNext(response -> log.info("isAlive: {}", response));
    }

}
