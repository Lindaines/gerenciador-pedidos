package com.lachonete.gerenciadorpedidos.adapters.in.controller;

import com.lachonete.gerenciadorpedidos.adapters.in.controller.mapper.OrderMapper;
import com.lachonete.gerenciadorpedidos.adapters.in.controller.request.OrderRequest;
import com.lachonete.gerenciadorpedidos.adapters.in.controller.response.OrderResponse;
import com.lachonete.gerenciadorpedidos.adapters.in.controller.response.ProductResponse;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderId;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductCategory;
import com.lachonete.gerenciadorpedidos.application.core.usecase.order.OrderCheckoutUseCase;
import com.lachonete.gerenciadorpedidos.application.ports.in.order.ListQueuedOrdersInputPort;
import com.lachonete.gerenciadorpedidos.application.ports.in.order.OrderCheckoutInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private OrderCheckoutUseCase orderCheckoutUseCase;
    @Autowired
    private OrderCheckoutInputPort orderCheckoutInputPort;
    @Autowired
    private ListQueuedOrdersInputPort listQueuedOrdersInputPort;
    @Autowired
    private OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<Long> checkout(@Valid @RequestBody OrderRequest orderRequest) {
        try {
            var order = orderMapper.toOrder(orderRequest);
            OrderId orderId = orderCheckoutInputPort.checkout(order);
            return ResponseEntity.ok().build();
        } catch (Exception IllegalArgumentException) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/queue")
    public ResponseEntity<List<OrderResponse>> queue() {
        var orders = listQueuedOrdersInputPort.getOrders();
        List<OrderResponse> response = orders.stream().map(order -> orderMapper.toOrderResponse(order)).toList();
        return ResponseEntity.ok().body(response);
    }

}
