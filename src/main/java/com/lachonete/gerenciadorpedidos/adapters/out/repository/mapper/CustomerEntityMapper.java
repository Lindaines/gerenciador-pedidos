package com.lachonete.gerenciadorpedidos.adapters.out.repository.mapper;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.CustomerEntity;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerEntityMapper {
    public CustomerEntity toCustomerEntity(Customer customer){
        return new CustomerEntity(
                customer.getCpf().toString(),
                customer.getName().toString(),
                customer.getEmail().toString());
    }
}
