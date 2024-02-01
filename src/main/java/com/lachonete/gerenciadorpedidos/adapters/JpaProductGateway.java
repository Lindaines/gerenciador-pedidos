package com.lachonete.gerenciadorpedidos.adapters;

import com.lachonete.gerenciadorpedidos.adapters.data.ProductData;
import com.lachonete.gerenciadorpedidos.adapters.repositories.ProductRepository;
import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Image;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Money;
import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductId;
import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JpaProductGateway implements ProductGateway {
    private final ProductRepository productRepository;

    public JpaProductGateway(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<Product> getAll() {
        return productRepository.findAll().stream().map(this::mapToProduct).toList();
    }

    private Product mapToProduct(ProductData productData) {
        return  Product.ProductBuilder.aProduct()
                .withId(new ProductId(productData.getId()))
                .withName(productData.getName())
                .withCategory(productData.getCategory())
                .withPrice(new Money(productData.getPrice()))
                .withDescription(productData.getDescription())
                .withImages(productData.getImages().stream().map(Image::new).toList())
                .build();
    }

    public ProductId add(Product product) {
        UUID id = UUID.randomUUID();
        ProductData productData = ProductData
                .builder()
                .id(id)
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice().getAmount())
                .images(product.getImages().stream().map(Image::toString).toList())
                .build();
        productRepository.save(productData);

        return new ProductId(id);
    }

    @Override
    public Product getById(UUID id) {
        return productRepository.findById(id)
          .map(this::mapToProduct)
                .orElse(null);
    }

    @Override
    public void update(Product product) {
        ProductData productData = ProductData
                .builder()
                .id(product.getId().getValue())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice().getAmount())
                .images(product.getImages().stream().map(Image::toString).toList())
                .build();
        productRepository.save(productData);    }

    @Override
    public void delete(UUID id) {
        productRepository.deleteById(id);
    }

}
