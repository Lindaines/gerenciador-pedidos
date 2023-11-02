package com.lachonete.gerenciadorpedidos.adapters.in.controller;

import com.lachonete.gerenciadorpedidos.adapters.in.controller.mapper.ProductMapper;
import com.lachonete.gerenciadorpedidos.adapters.in.controller.request.ProductRequest;
import com.lachonete.gerenciadorpedidos.adapters.in.controller.response.ProductResponse;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductCategory;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductId;
import com.lachonete.gerenciadorpedidos.application.ports.in.product.*;

import javax.validation.Valid;

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
    private UpdateProductInputPort updateProductInputPort;
    @Autowired
    private DeleteProductInputPort deleteProductInputPort;
    @Autowired
    private ListProductsByCategoryInputPort listProductsByCategoryInputPort;
    @Autowired
    private FindProductByIdInputPort findProductByIdInputPort;
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
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable final UUID id, @Valid @RequestBody ProductRequest productRequest) {
        try {
            var product = productMapper.toProduct(productRequest);
            product.setId(new ProductId(id));
            updateProductInputPort.update(product);
            return ResponseEntity.ok().build();
        } catch (Exception IllegalArgumentException) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final UUID id) {
        try {
            deleteProductInputPort.delete(new ProductId(id));
            return ResponseEntity.ok().build();
        } catch (Exception IllegalArgumentException) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/find")
    public ResponseEntity<List<ProductResponse>> findByCategory(@RequestParam(name = "category") final ProductCategory category) {
        var products = listProductsByCategoryInputPort.find(category);
        var productsResponse = products.stream().map(product -> productMapper.toProductResponse(product)).toList();
        return ResponseEntity.ok().body(productsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable final UUID id) {
        var product = findProductByIdInputPort.findById(new ProductId(id));
        var productsResponse = productMapper.toProductResponse(product);
        return ResponseEntity.ok().body(productsResponse);
    }

}
