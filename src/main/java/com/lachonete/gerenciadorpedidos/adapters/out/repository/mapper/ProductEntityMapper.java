package com.lachonete.gerenciadorpedidos.adapters.out.repository.mapper;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.CustomerEntity;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.ProductEntity;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Customer;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.*;
import jdk.jfr.Category;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityMapper {
    public ProductEntity toProductEntity(Product product){
        var images = product.getImages().stream().map(image -> image.urlPath).toList();
        return ProductEntity.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice().getAmount())
                .category(product.getCategory())
                .images(images).build();
    }

    public Product toProduct(ProductEntity productEntity){
        var images = productEntity.getImages().stream().map(Image::new).toList();
        return Product.ProductBuilder.aProduct()
                .withId(new ProductId(productEntity.getId()))
                .withName(productEntity.getName())
                .withCategory(productEntity.getCategory())
                .withPrice(new Money(productEntity.getPrice()))
                .withDescription(productEntity.getDescription())
                .withImages(images)
                .build();

    }
}
