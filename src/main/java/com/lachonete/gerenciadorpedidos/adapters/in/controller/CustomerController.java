package com.lachonete.gerenciadorpedidos.adapters.in.controller;

import com.lachonete.gerenciadorpedidos.adapters.in.controller.mapper.CustomerMapper;
import com.lachonete.gerenciadorpedidos.adapters.in.controller.request.CustomerRequest;
import com.lachonete.gerenciadorpedidos.adapters.in.controller.response.CustomerResponse;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.CustomerId;
import com.lachonete.gerenciadorpedidos.application.ports.in.CreateCustomerInputPort;
import com.lachonete.gerenciadorpedidos.application.ports.in.IdentifyCustomerInputPort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    @Autowired
    private CreateCustomerInputPort createCustomerInputPort;
    @Autowired
    private IdentifyCustomerInputPort identifyCustomerInputPort;
    @Autowired
    private CustomerMapper customerMapper;
    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CustomerRequest customerRequest){
        var customer = customerMapper.toCustomer(customerRequest);
        createCustomerInputPort.create(customer);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable final UUID id) {
        var customerId = new CustomerId(id);
        var customer = identifyCustomerInputPort.getCustomer(customerId);
        if (customer.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var customerResponse = customerMapper.toCustomerResponse(customer.get());
        return ResponseEntity.ok().body(customerResponse);
    }
}
