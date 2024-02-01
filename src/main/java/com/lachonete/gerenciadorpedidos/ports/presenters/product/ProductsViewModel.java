package com.lachonete.gerenciadorpedidos.ports.presenters.product;

import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ProductsViewModel {
    @Singular("getProductsViewModel") private List<ProductViewModel> products;
}
