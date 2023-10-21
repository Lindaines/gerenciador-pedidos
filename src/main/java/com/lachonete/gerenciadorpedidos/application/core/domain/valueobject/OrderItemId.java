package com.lachonete.gerenciadorpedidos.application.core.domain.valueobject;

import java.util.UUID;

public class OrderItemId extends BaseId<UUID> {
    public OrderItemId(UUID value) {
        super(value);
    }
}
