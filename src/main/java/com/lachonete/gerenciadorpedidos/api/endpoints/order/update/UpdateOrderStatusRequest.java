package com.lachonete.gerenciadorpedidos.api.endpoints.order.update;

import com.lachonete.gerenciadorpedidos.entities.valueobject.OrderStatus;
import com.lachonete.gerenciadorpedidos.entities.valueobject.PaymentStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateOrderStatusRequest {
    private OrderStatus orderStatus;
}
