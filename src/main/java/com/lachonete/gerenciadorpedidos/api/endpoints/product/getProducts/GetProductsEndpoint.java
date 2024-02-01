package com.lachonete.gerenciadorpedidos.api.endpoints.product.getProducts;


import com.lachonete.gerenciadorpedidos.api.endpoints.BaseEndpoint;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductsOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.GetProductsInputBoundary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class GetProductsEndpoint implements BaseEndpoint {
    private final GetProductsInputBoundary useCase;
    private final ProductsOutputBoundary presenter;

    public GetProductsEndpoint(GetProductsInputBoundary useCase, ProductsOutputBoundary presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    @GetMapping
    public ResponseEntity execute() {
        useCase.execute();

        return ResponseEntity.ok(presenter.getViewModel());
    }
}
