package com.lachonete.gerenciadorpedidos.presenters;

import com.lachonete.gerenciadorpedidos.ports.presenters.ProductViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductsResponse;

import java.time.format.DateTimeFormatter;

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
