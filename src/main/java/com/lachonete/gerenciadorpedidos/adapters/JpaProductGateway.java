package com.lachonete.gerenciadorpedidos.adapters;

import com.lachonete.gerenciadorpedidos.adapters.data.ProductData;
import com.lachonete.gerenciadorpedidos.adapters.repositories.ProductRepository;
import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Image;
import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductId;
import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.UUID;

@Component
public class JpaProductGateway implements ProductGateway {
    private final ProductRepository productRepository;

    public JpaProductGateway(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Collection<Product> getAll() {
        return null;
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
    public Product getById(ProductId id) {
        return null;
    }

    @Override
    public void update(Product product) {

    }

}
