package com.lachonete.gerenciadorpedidos.application.core.usecase.customer;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Customer;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.CustomerId;
import com.lachonete.gerenciadorpedidos.application.ports.in.customer.IdentifyCustomerInputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.customer.IdentifyCustomerOutputPort;

import java.util.Optional;


public class IdentifyCustomerUseCase implements IdentifyCustomerInputPort {
    private final IdentifyCustomerOutputPort identifyCustomerOutputPort;

    public IdentifyCustomerUseCase(IdentifyCustomerOutputPort identifyCustomerOutputPort) {
        this.identifyCustomerOutputPort = identifyCustomerOutputPort;
    }


    @Override
    public Optional<Customer> getCustomer(CustomerId customerId) {
        return identifyCustomerOutputPort.getCustomer(customerId);
    }
}
