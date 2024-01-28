package com.lachonete.gerenciadorpedidos.api.endpoints.product.deleteProducts;


import com.lachonete.gerenciadorpedidos.api.endpoints.BaseEndpoint;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.remove.RemoveProductInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.remove.RemoveProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class DeleteProductByIdEndpoint implements BaseEndpoint {
    private final RemoveProductInputBoundary useCase;

    public DeleteProductByIdEndpoint(RemoveProductInputBoundary useCase) {
        this.useCase = useCase;
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity execute(@PathVariable final UUID productId) {
        useCase.execute(RemoveProductRequest.builder().id(productId).build());
        return ResponseEntity.noContent().build();
    }
}
