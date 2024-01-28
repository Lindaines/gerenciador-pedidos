package com.lachonete.gerenciadorpedidos.api.endpoints.customer.add;


import com.lachonete.gerenciadorpedidos.api.endpoints.BaseEndpoint;
import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerCreatedOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductCreatedOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.add.AddCustomerInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.add.AddCustomerRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.add.AddProductInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.add.AddProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.text.MessageFormat;

@RestController
@RequestMapping("/api/v1/customers")
public class AddCustomerEndpoint implements BaseEndpoint {
    private final AddCustomerInputBoundary useCase;
    private final CustomerCreatedOutputBoundary presenter;

    public AddCustomerEndpoint(AddCustomerInputBoundary useCase, CustomerCreatedOutputBoundary presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    @PostMapping
    public ResponseEntity execute(@RequestBody @Valid NewCustomerRequest request) {
        useCase.execute(
                AddCustomerRequest
                        .builder()
                        .name(request.getName())
                        .cpf(request.getCpf())
                        .email(request.getEmail())
                        .build()
        );

        return ResponseEntity
                .created(
                        URI.create(
                                MessageFormat.format("/api/v1/customers/{0}", presenter.getViewModel().getId())
                        )
                )
                .body(presenter.getViewModel());
    }
}
