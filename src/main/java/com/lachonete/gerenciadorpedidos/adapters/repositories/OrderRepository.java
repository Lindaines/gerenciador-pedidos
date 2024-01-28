package com.lachonete.gerenciadorpedidos.adapters.repositories;

import com.lachonete.gerenciadorpedidos.adapters.data.OrderData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends CrudRepository<OrderData, UUID> {
    List<OrderData> findAll();
}
