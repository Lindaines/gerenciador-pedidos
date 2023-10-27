package com.lachonete.gerenciadorpedidos.application.ports.in.customer;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Customer;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.CustomerId;

import java.util.Optional;

public interface IdentifyCustomerInputPort {
    Optional<Customer> getCustomer(CustomerId customerId);
}
