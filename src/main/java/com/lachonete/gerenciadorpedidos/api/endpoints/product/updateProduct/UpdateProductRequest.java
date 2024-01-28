package com.lachonete.gerenciadorpedidos.api.endpoints.product.updateProduct;

import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Data
public class UpdateProductRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private BigDecimal price;
    private ProductCategory category;
    private List<String> images;
}
