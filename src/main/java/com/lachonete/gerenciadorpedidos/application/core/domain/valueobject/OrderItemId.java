package com.lachonete.gerenciadorpedidos.application.core.domain.valueobject;

import java.util.UUID;

public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long value) {
        super(value);
    }
}
