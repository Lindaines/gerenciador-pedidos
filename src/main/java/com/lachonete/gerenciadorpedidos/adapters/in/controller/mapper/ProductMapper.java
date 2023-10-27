package com.lachonete.gerenciadorpedidos.adapters.in.controller.mapper;

import com.lachonete.gerenciadorpedidos.adapters.in.controller.request.CustomerRequest;
import com.lachonete.gerenciadorpedidos.adapters.in.controller.request.ProductRequest;
import com.lachonete.gerenciadorpedidos.adapters.in.controller.response.CustomerResponse;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Customer;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.*;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toProduct(ProductRequest productRequest){
        var name = productRequest.getName();
        var description = productRequest.getDescription();
        var price = new Money(productRequest.getPrice());
        var category = productRequest.getCategory();
        var images = productRequest.getImages().stream()
                .map(Image::new).toList();

        return Product.ProductBuilder.aProduct()
                .withName(name)
                .withDescription(description)
                .withPrice(price)
                .withCategory(ProductCategory.valueOf(category))
                .withImages(images).build();
    }
}
