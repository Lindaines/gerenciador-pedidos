package com.lachonete.gerenciadorpedidos.presenters.product;

import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductResponse;

public class BaseProductPresenter {

    protected BaseProductPresenter() { }

    public static ProductViewModel mapToProductViewModel(ProductResponse responseModel) {
        return ProductViewModel
                .builder()
                .id(responseModel.getId().toString())
                .name(responseModel.getName())
                .description(responseModel.getDescription())
                .category(responseModel.getCategory())
                .price(responseModel.getPrice())
                .images(responseModel.getImages())
                .build();
    }
}
