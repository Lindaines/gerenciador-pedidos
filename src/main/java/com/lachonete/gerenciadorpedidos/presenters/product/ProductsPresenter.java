package com.lachonete.gerenciadorpedidos.presenters.product;

import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductsOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductsViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductsResponse;


public class ProductsPresenter extends BaseProductPresenter implements ProductsOutputBoundary {
    private ProductsViewModel viewModel;

    public ProductsViewModel getViewModel() {
        return viewModel;
    }

    public void present(ProductsResponse responseModel) {
        ProductsViewModel.ProductsViewModelBuilder productsViewModelBuilder = ProductsViewModel.builder();
        responseModel.getProducts()
                .stream()
                .map(BaseProductPresenter::mapToProductViewModel)
                .forEach(productsViewModelBuilder::getProductsViewModel);
        viewModel = productsViewModelBuilder.build();
    }
}

