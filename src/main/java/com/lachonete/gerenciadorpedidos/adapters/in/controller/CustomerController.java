package com.lachonete.gerenciadorpedidos.adapters.in.controller;

import com.lachonete.gerenciadorpedidos.adapters.in.controller.mapper.CustomerMapper;
import com.lachonete.gerenciadorpedidos.adapters.in.controller.request.CustomerRequest;
import com.lachonete.gerenciadorpedidos.application.core.usecase.CreateCustomerUseCase;
import com.lachonete.gerenciadorpedidos.application.ports.in.CreateCustomerInputPort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    @Autowired
    private CreateCustomerInputPort createCustomerInputPort;
    private CustomerMapper customerMapper;
    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CustomerRequest customerRequest){
        var customer = customerMapper.toCustomer(customerRequest);
        createCustomerInputPort.create(customer);
        return ResponseEntity.ok().build();
    }
}
