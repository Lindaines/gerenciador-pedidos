package com.lachonete.gerenciadorpedidos.application.core.domain.entity;

import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.CustomerId;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.Money;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderId;

import java.util.List;

public class Order extends AggregateRoot<OrderId>{
    private CustomerId customerId;
    private Money price;
    private List<OrderItem> items;

    // to do: implement domain validations
}
