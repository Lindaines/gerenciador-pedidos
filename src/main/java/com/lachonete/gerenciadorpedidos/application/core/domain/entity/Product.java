package com.lachonete.gerenciadorpedidos.application.core.domain.entity;

import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.Image;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.Money;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductCategory;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductId;

import java.util.List;

public class Product extends BaseEntity<ProductId> {
    private String name;
    private String description;
    private Money price;
    private ProductCategory category;
    private List<Image> images;
}
