package com.lachonete.gerenciadorpedidos.adapters.out.repository.mapper;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.CustomerEntity;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Customer;

public class CustomerEntityMapper {
    public CustomerEntity toCustomerEntity(Customer customer){
        return new CustomerEntity(
                customer.getId().getValue(),
                customer.getCpf().toString(),
                customer.getName().toString(),
                customer.getEmail().toString());
    }
}
