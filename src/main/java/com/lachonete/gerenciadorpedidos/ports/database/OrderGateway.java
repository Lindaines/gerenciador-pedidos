package com.lachonete.gerenciadorpedidos.ports.database;


import com.lachonete.gerenciadorpedidos.entities.Customer;
import com.lachonete.gerenciadorpedidos.entities.Order;
import com.lachonete.gerenciadorpedidos.entities.valueobject.CustomerId;

import java.util.List;
import java.util.UUID;

public interface OrderGateway {
    List<Order> getAll();
    Order add(Order order);
    Order getById(UUID id);

    public interface BadRequest {}
    public interface NotFound {}

    public class CustomerAlreadyByNameExistsException extends RuntimeException implements BadRequest {
    }

    public class CustomerNotFoundException extends RuntimeException implements NotFound {
    }

}
