package com.lachonete.gerenciadorpedidos.ports.usescases.product.get;

import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ProductsResponse {
    @Singular("addProduct") private List<ProductResponse> products;

}
