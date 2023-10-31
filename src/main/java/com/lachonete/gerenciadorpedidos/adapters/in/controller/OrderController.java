package com.lachonete.gerenciadorpedidos.adapters.in.controller;

import com.lachonete.gerenciadorpedidos.adapters.in.controller.mapper.OrderMapper;
import com.lachonete.gerenciadorpedidos.adapters.in.controller.request.OrderRequest;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderId;
import com.lachonete.gerenciadorpedidos.application.core.usecase.order.OrderCheckoutUseCase;
import com.lachonete.gerenciadorpedidos.application.ports.in.order.OrderCheckoutInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private OrderCheckoutUseCase orderCheckoutUseCase;
    @Autowired
    private OrderCheckoutInputPort orderCheckoutInputPort;
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


}
