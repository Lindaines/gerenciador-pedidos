package com.lachonete.gerenciadorpedidos.adapters.out.repository.customer;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
}
