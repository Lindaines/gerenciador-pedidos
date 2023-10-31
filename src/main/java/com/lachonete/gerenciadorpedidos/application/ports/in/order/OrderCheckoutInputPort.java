package com.lachonete.gerenciadorpedidos.application.ports.in.order;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Order;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.OrderItem;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderId;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductCategory;

import java.util.List;

public interface OrderCheckoutInputPort {
    OrderId checkout(Order order);

    void loadPriceProductsInfo (OrderItem orderitem);
    void validateOrderItemsInfo (Order order);
}