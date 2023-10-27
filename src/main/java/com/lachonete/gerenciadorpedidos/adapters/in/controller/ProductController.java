package com.lachonete.gerenciadorpedidos.adapters.in.controller;

import com.lachonete.gerenciadorpedidos.adapters.in.controller.mapper.CustomerMapper;
import com.lachonete.gerenciadorpedidos.adapters.in.controller.mapper.ProductMapper;
import com.lachonete.gerenciadorpedidos.adapters.in.controller.request.CustomerRequest;
import com.lachonete.gerenciadorpedidos.adapters.in.controller.request.ProductRequest;
import com.lachonete.gerenciadorpedidos.adapters.in.controller.response.CustomerResponse;
import com.lachonete.gerenciadorpedidos.adapters.in.controller.response.ProductResponse;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.CustomerId;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductCategory;
import com.lachonete.gerenciadorpedidos.application.ports.in.customer.CreateCustomerInputPort;
import com.lachonete.gerenciadorpedidos.application.ports.in.customer.IdentifyCustomerInputPort;
import com.lachonete.gerenciadorpedidos.application.ports.in.product.CreateProductInputPort;
import com.lachonete.gerenciadorpedidos.application.ports.in.product.ListProductsByCategoryInputPort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private CreateProductInputPort createProductInputPort;
    @Autowired
    private ListProductsByCategoryInputPort listProductsByCategoryInputPort;
    @Autowired
    private ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody ProductRequest productRequest) {
        try {
            var product = productMapper.toProduct(productRequest);
            createProductInputPort.create(product);
            return ResponseEntity.ok().build();
        } catch (Exception IllegalArgumentException) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/find")
    public ResponseEntity<List<ProductResponse>> findByCategory(@RequestParam(name = "category") final String category) {
        var products = listProductsByCategoryInputPort.find(ProductCategory.valueOf(category));
        var productsResponse = products.stream().map(product -> productMapper.toProductResponse(product)).toList();
        return ResponseEntity.ok().body(productsResponse);
    }

}
