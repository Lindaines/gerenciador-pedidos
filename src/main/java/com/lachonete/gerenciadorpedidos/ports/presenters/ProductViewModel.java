package com.lachonete.gerenciadorpedidos.ports.presenters;

import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class ProductViewModel {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private ProductCategory category;
    private List<String> images;
}
