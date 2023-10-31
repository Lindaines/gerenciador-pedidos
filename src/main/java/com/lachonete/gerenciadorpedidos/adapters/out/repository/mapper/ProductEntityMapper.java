package com.lachonete.gerenciadorpedidos.adapters.out.repository.mapper;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.ProductEntity;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.*;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityMapper {
    public ProductEntity toProductEntity(com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product product){
        var images = product.getImages().stream().map(image -> image.urlPath).toList();
        return ProductEntity.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice().getAmount())
                .category(product.getCategory())
                .images(images).build();
    }

    public com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product toProduct(ProductEntity productEntity){
        var images = productEntity.getImages().stream().map(Image::new).toList();
        return com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product.ProductBuilder.aProduct()
                .withId(new ProductId(productEntity.getId()))
                .withName(productEntity.getName())
                .withCategory(productEntity.getCategory())
                .withPrice(new Money(productEntity.getPrice()))
                .withDescription(productEntity.getDescription())
                .withImages(images)
                .build();

    }
}
