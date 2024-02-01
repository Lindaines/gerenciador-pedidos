package com.lachonete.gerenciadorpedidos.api.endpoints.customer.get;


import com.lachonete.gerenciadorpedidos.api.endpoints.BaseEndpoint;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductsOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.GetProductsInputBoundary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class GetCustomersEndpoint implements BaseEndpoint {
    private final GetProductsInputBoundary useCase;
    private final ProductsOutputBoundary presenter;

    public GetCustomersEndpoint(GetProductsInputBoundary useCase, ProductsOutputBoundary presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    @GetMapping
    public ResponseEntity execute() {
        useCase.execute();

        return ResponseEntity.ok(presenter.getViewModel());
    }
}
