package com.lachonete.gerenciadorpedidos.application.core.usecase;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Customer;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.CustomerId;
import com.lachonete.gerenciadorpedidos.application.ports.in.CreateCustomerInputPort;
import com.lachonete.gerenciadorpedidos.application.ports.in.IdentifyCustomerInputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.CreateCustomerOutputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.IdentifyCustomerOutputPort;

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
