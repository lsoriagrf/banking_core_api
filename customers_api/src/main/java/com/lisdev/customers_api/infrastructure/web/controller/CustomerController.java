package com.lisdev.customers_api.infrastructure.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lisdev.customers_api.application.port.in.CustomerPortIn;
import com.lisdev.customers_api.common.WebAdapter;
import com.lisdev.customers_api.infrastructure.web.dto.request.CreateCustomer;
import com.lisdev.customers_api.infrastructure.web.dto.request.UpdateCustomer;
import com.lisdev.customers_api.infrastructure.web.dto.response.CustomerResolvedResponse;
import com.lisdev.customers_api.infrastructure.web.dto.response.CustomerIdResponse;
import com.lisdev.customers_api.infrastructure.web.dto.response.CustomerResponse;
import com.lisdev.customers_api.infrastructure.web.mapper.CustomerWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerPortIn customerPortIn;
    private final CustomerWebMapper customerWebMapper;

    @PostMapping
    public Mono<ResponseEntity<CustomerResponse>> createCustomer(
            @Valid @RequestBody CreateCustomer body) {
        return customerPortIn.createCustomer(customerWebMapper.toCreateCommand(body))
                .map(customerWebMapper::toResponse)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CustomerResponse>> updateCustomer(
            @PathVariable String id,
            @Valid @RequestBody UpdateCustomer body) {
        return customerPortIn.updateCustomer(customerWebMapper.toUpdateCommand(body))
                .map(customerWebMapper::toResponse)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{identification}")
    public Mono<ResponseEntity<CustomerResponse>> findCustomerByIdentification(
            @PathVariable String identification) {
        return customerPortIn.findCustomerByIdentification(identification)
                .map(customerWebMapper::toResponse)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable Integer id) {
        return customerPortIn.deleteCustomer(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }

    // Gets customer id by identification query param.
    @GetMapping
    public Mono<ResponseEntity<CustomerIdResponse>> findCustomerIdByIdentification(
            @RequestParam String identification) {
        return customerPortIn.findIdByIdentification(identification)
                .map(CustomerIdResponse::new)
                .map(ResponseEntity::ok);
    }

    // Gets active customer identification and full name by id.
    @GetMapping("/resolve")
    public Mono<ResponseEntity<CustomerResolvedResponse>> findActiveCustomerIdentificationAndFullNameById(
            @RequestParam Integer id) {
        return customerPortIn.findActiveCustomerIdentificationAndFullNameById(id)
                .map(customerWebMapper::toResolvedResponse)
                .map(ResponseEntity::ok);
    }

}
