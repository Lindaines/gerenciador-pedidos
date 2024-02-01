package com.lachonete.gerenciadorpedidos.api.endpoints.order;


import com.lachonete.gerenciadorpedidos.api.endpoints.BaseEndpoint;
import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrdersOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductsOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.GetOrdersInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.GetProductsInputBoundary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class GetOrdersEndpoint implements BaseEndpoint {
    private final GetOrdersInputBoundary useCase;
    private final OrdersOutputBoundary presenter;

    public GetOrdersEndpoint(GetOrdersInputBoundary useCase, OrdersOutputBoundary presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    @GetMapping
    public ResponseEntity execute() {
        useCase.execute();
        return ResponseEntity.ok(presenter.getViewModel());
    }
}
