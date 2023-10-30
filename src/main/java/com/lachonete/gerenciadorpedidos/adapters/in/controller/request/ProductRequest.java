package com.lachonete.gerenciadorpedidos.adapters.in.controller.request;

import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductCategory;
import javax.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private BigDecimal price;
    private ProductCategory category;
    private List<String> images;
}
