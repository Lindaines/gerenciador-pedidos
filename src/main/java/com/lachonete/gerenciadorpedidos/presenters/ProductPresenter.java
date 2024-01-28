package com.lachonete.gerenciadorpedidos.presenters;

import com.lachonete.gerenciadorpedidos.ports.presenters.ProductOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.ProductViewModel;
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

