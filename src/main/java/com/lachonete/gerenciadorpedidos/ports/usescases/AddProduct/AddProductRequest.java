package com.lachonete.gerenciadorpedidos.ports.usescases.AddProduct;

import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddProductRequest {
        private String name;
        private String description;
        private BigDecimal price;
        private ProductCategory category;
        private List<String> images;
    }

