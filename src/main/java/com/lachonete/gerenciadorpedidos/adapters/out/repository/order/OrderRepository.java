package com.lachonete.gerenciadorpedidos.adapters.out.repository.order;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.OrderEntity;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.ProductEntity;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderId;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderStatus;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findByOrderStatus(OrderStatus status);

}
