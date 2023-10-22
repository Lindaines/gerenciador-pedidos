package com.lachonete.gerenciadorpedidos.adapters.out.repository;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
}
