package com.lachonete.gerenciadorpedidos.presenters.product;

import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductResponse;


public class ProductPresenter extends BaseProductPresenter implements ProductOutputBoundary {
    private ProductViewModel viewModel;

    public ProductViewModel getViewModel() {
        return viewModel;
    }

    public void present(ProductResponse responseModel) {
        viewModel = mapToProductViewModel(responseModel);
    }
}

