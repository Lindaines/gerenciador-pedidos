package com.lachonete.gerenciadorpedidos.application.ports.out;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Customer;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.CustomerId;

import java.util.Optional;

public interface IdentifyCustomerOutputPort {
    Optional<Customer> getCustomer (CustomerId customerId);
}
