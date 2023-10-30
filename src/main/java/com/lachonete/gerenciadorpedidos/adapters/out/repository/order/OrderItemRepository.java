package com.lachonete.gerenciadorpedidos.adapters.out.repository.order;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.OrderItemEntity;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.OrderItemEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, OrderItemEntityId> {
}

