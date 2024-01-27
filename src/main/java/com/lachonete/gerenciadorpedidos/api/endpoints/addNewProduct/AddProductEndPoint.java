package com.lachonete.gerenciadorpedidos.api.endpoints.addNewProduct;


import com.lachonete.gerenciadorpedidos.ports.presenters.ProductCreatedOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.AddProduct.AddProductInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.AddProduct.AddProductRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.text.MessageFormat;

@RestController
@RequestMapping("/api/v1/products")
public class AddProductEndPoint implements BaseEndpoint {
    private final AddProductInputBoundary useCase;
    private final ProductCreatedOutputBoundary presenter;

    public AddProductEndPoint(AddProductInputBoundary useCase, ProductCreatedOutputBoundary presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    @PostMapping
    public ResponseEntity execute(@RequestBody @Valid NewProductRequest request) {
        useCase.execute(
                AddProductRequest
                        .builder()
                        .name(request.getName())
                        .description(request.getDescription())
                        .price(request.getPrice())
                        .category(request.getCategory())
                        .images(request.getImages())
                        .build()
        );

        return ResponseEntity
                .created(
                        URI.create(
                                MessageFormat.format("/api/v1/products/{0}", presenter.getViewModel().getId())
                        )
                )
                .body(presenter.getViewModel());
    }
}
