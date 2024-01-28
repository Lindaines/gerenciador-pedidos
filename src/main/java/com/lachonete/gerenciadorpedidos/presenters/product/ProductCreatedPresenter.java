package com.lachonete.gerenciadorpedidos.presenters.product;

import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductCreatedOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductCreatedViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.add.NewProductResponse;

public class ProductCreatedPresenter implements ProductCreatedOutputBoundary{
    private ProductCreatedViewModel viewModel;

    public ProductCreatedViewModel getViewModel() {
        return viewModel;
    }

    public void present(NewProductResponse responseModel) {
        viewModel = new ProductCreatedViewModel(responseModel.getId().toString());
    }
}
