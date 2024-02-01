package com.lachonete.gerenciadorpedidos.ports.presenters.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lachonete.gerenciadorpedidos.adapters.data.OrderData;
import com.lachonete.gerenciadorpedidos.adapters.data.OrderItemData;
import com.lachonete.gerenciadorpedidos.entities.valueobject.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class OrderItemViewModel {
    private Long id;
    private UUID productId;
    private BigDecimal subTotal;
    private int  quantity;
}