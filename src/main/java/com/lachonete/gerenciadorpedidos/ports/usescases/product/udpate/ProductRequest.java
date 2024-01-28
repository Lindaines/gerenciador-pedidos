package com.lachonete.gerenciadorpedidos.ports.usescases.product.udpate;

import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
        private UUID id;
        private String name;
        private String description;
        private BigDecimal price;
        private ProductCategory category;
        private List<String> images;
    }

