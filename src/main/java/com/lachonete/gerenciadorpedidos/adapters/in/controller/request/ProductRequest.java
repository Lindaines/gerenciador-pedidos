package com.lachonete.gerenciadorpedidos.adapters.in.controller.request;

import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.Image;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.Money;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private List<String> images;
}
