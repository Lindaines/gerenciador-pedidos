package com.lachonete.gerenciadorpedidos.adapters.repositories;

import com.lachonete.gerenciadorpedidos.adapters.data.CustomerData;
import com.lachonete.gerenciadorpedidos.adapters.data.ProductData;
import com.lachonete.gerenciadorpedidos.entities.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends CrudRepository<CustomerData, UUID> {
    List<CustomerData> findAll();
}
