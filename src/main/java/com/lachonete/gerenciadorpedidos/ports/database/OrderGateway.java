package com.lachonete.gerenciadorpedidos.ports.database;


import com.lachonete.gerenciadorpedidos.entities.Customer;
import com.lachonete.gerenciadorpedidos.entities.Order;
import com.lachonete.gerenciadorpedidos.entities.valueobject.CustomerId;
import com.lachonete.gerenciadorpedidos.entities.valueobject.OrderStatus;
import com.lachonete.gerenciadorpedidos.entities.valueobject.PaymentStatus;

import java.util.List;
import java.util.UUID;

public interface OrderGateway {
    List<Order> getAll();
    List<Order> getAllExceptFinished();
    Order add(Order order);
    Order getById(UUID id);
    void updateStatus(UUID id, OrderStatus paymentStatus);
    public interface BadRequest {}
    public interface NotFound {}


    public class OrderNotFoundException extends RuntimeException implements NotFound {
    }

}
