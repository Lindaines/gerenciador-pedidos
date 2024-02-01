package com.lachonete.gerenciadorpedidos.api.endpoints.order;


import com.lachonete.gerenciadorpedidos.api.endpoints.BaseEndpoint;
import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrderCreatedOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.add.CheckoutOrderInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.add.CheckoutOrderRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.add.OrderItemRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.text.MessageFormat;

@RestController
@RequestMapping("/api/v1/orders")
public class CheckoutOrderEndpoint implements BaseEndpoint {
    private final CheckoutOrderInputBoundary useCase;
    private final OrderCreatedOutputBoundary presenter;

    public CheckoutOrderEndpoint(CheckoutOrderInputBoundary useCase, OrderCreatedOutputBoundary presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    @PostMapping
    public ResponseEntity execute(@RequestBody @Valid NewOrderRequest request) {
        var orderItems = request.getItems().stream().map(orderItemRequest -> OrderItemRequest.builder()
                .price(orderItemRequest.getPrice())
                .productId(orderItemRequest.getProductId())
                .quantity(orderItemRequest.getQuantity())
                .subtotal(orderItemRequest.getSubtotal())
                .build()).toList();
        useCase.execute(CheckoutOrderRequest.builder().
                items(orderItems)
                .build());

        return ResponseEntity
                .created(
                        URI.create(
                                MessageFormat.format("/api/v1/orders/{0}", presenter.getViewModel().getId())
                        )
                )
                .body(presenter.getViewModel());
    }
}
