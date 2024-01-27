package com.lachonete.gerenciadorpedidos.api.endpoints.addNewProduct;

import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Data
public class NewProductRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private BigDecimal price;
    private ProductCategory category;
    private List<String> images;
}
