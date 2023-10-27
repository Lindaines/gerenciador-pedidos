package com.lachonete.gerenciadorpedidos.adapters.in.controller.response;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ProductResponse {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private List<String> images;
}
