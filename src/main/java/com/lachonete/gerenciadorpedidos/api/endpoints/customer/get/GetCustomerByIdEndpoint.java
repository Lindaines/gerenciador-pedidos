package com.lachonete.gerenciadorpedidos.api.endpoints.customer.get;


import com.lachonete.gerenciadorpedidos.api.endpoints.BaseEndpoint;
import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.GetCustomerByIdRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.GetCustomerInputBoundary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class GetCustomerByIdEndpoint implements BaseEndpoint {
    private final GetCustomerInputBoundary useCase;
    private final CustomerOutputBoundary presenter;

    public GetCustomerByIdEndpoint(GetCustomerInputBoundary useCase, CustomerOutputBoundary presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity execute(@PathVariable final UUID customerId) {
        useCase.execute(GetCustomerByIdRequest.builder().id(customerId).build());
        return ResponseEntity.ok(presenter.getViewModel());
    }
}
