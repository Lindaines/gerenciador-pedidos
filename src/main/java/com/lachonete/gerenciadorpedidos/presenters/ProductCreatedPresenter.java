package com.lachonete.gerenciadorpedidos.presenters;

import com.lachonete.gerenciadorpedidos.ports.presenters.ProductCreatedOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.ProductCreatedViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.AddProduct.NewProductResponse;

public class ProductCreatedPresenter implements ProductCreatedOutputBoundary{
    private ProductCreatedViewModel viewModel;

    public ProductCreatedViewModel getViewModel() {
        return viewModel;
    }

    public void present(NewProductResponse responseModel) {
        viewModel = new ProductCreatedViewModel(responseModel.getId().toString());
    }
}
