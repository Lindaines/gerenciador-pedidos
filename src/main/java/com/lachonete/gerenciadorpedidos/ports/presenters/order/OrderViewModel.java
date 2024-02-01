package com.lachonete.gerenciadorpedidos.ports.presenters.order;

import com.lachonete.gerenciadorpedidos.adapters.data.OrderItemData;
import com.lachonete.gerenciadorpedidos.entities.valueobject.OrderStatus;
import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class OrderViewModel {
    private UUID id;
    private BigDecimal price;
    private Integer pickUpCode;
    private OrderStatus orderStatus;
    private List<OrderItemViewModel> items;
}