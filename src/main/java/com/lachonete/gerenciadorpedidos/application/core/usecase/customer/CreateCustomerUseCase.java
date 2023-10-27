package com.lachonete.gerenciadorpedidos.application.core.usecase.customer;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Customer;
import com.lachonete.gerenciadorpedidos.application.ports.in.customer.CreateCustomerInputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.customer.CreateCustomerOutputPort;


public class CreateCustomerUseCase implements CreateCustomerInputPort {
    private final CreateCustomerOutputPort createCustomerOutputPort;

    public CreateCustomerUseCase(CreateCustomerOutputPort createCustomerOutputPort) {
        this.createCustomerOutputPort = createCustomerOutputPort;
    }

    @Override
    public void create(Customer customer){
        createCustomerOutputPort.insert(customer);
    }
}
