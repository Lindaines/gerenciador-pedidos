package com.lachonete.gerenciadorpedidos.api.endpoints.product.updateProduct;


import com.lachonete.gerenciadorpedidos.api.endpoints.BaseEndpoint;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.remove.RemoveProductInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.remove.RemoveProductRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.udpate.ProductRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.udpate.UpdateProductInputBoundary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class UpdateProductEndpoint implements BaseEndpoint {
    private final UpdateProductInputBoundary useCase;

    public UpdateProductEndpoint(UpdateProductInputBoundary useCase) {
        this.useCase = useCase;
    }

    @PutMapping("/{productId}")
    public ResponseEntity execute(@PathVariable final UUID productId, @Valid @RequestBody UpdateProductRequest request) {
        useCase.execute(ProductRequest.builder()
                .id(productId)
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .images(request.getImages())
                .build());
        return ResponseEntity.noContent().build();
    }
}
