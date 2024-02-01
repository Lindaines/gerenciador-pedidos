package com.lachonete.gerenciadorpedidos.adapters.repositories;

import com.lachonete.gerenciadorpedidos.adapters.data.OrderData;
import com.lachonete.gerenciadorpedidos.adapters.data.PaymentData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends CrudRepository<PaymentData, UUID> {
    List<PaymentData> findAll();
}
