package com.lachonete.gerenciadorpedidos.api.endpoints.getProducts;


import com.lachonete.gerenciadorpedidos.api.endpoints.BaseEndpoint;
import com.lachonete.gerenciadorpedidos.ports.presenters.ProductOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.GetProductByIdRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.GetProductInputBoundary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class GetProductByIdEndpoint implements BaseEndpoint {
    private final GetProductInputBoundary useCase;
    private final ProductOutputBoundary presenter;

    public GetProductByIdEndpoint(GetProductInputBoundary useCase, ProductOutputBoundary presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    @GetMapping("/{productId}")
    public ResponseEntity execute(@PathVariable final UUID productId) {
        useCase.execute(GetProductByIdRequest.builder().id(productId).build());
        return ResponseEntity.ok(presenter.getViewModel());
    }
}
