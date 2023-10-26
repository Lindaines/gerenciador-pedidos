package com.lachonete.gerenciadorpedidos.adapters.out.repository;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.mapper.CustomerEntityMapper;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Customer;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.CustomerId;
import com.lachonete.gerenciadorpedidos.application.ports.in.IdentifyCustomerInputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.IdentifyCustomerOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IdentifyCustomerAdapter implements IdentifyCustomerOutputPort {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerEntityMapper customerEntityMapper;

    @Override
    public Optional<Customer> getCustomer(CustomerId customerId) {
        var customerEntity = customerRepository.findById(customerId.getValue());
        return customerEntity.map(entity -> customerEntityMapper.toCustomer(entity));
    }
}
