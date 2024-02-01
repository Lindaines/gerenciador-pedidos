package com.lachonete.gerenciadorpedidos.presenters.order;

import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrdersOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrdersViewModel;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductsOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductsViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrderResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrdersResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductsResponse;
import com.lachonete.gerenciadorpedidos.presenters.product.BaseProductPresenter;


public class OrdersPresenter extends BaseOrderPresenter implements OrdersOutputBoundary {
    private OrdersViewModel viewModel;

    public OrdersViewModel getViewModel() {
        return viewModel;
    }

    public void present(OrdersResponse responseModel) {
        OrdersViewModel.OrdersViewModelBuilder ordersViewModelBuilder = OrdersViewModel.builder();
        responseModel.getOrders()
                .stream()
                .map(BaseOrderPresenter::mapToOrderViewModel)
                .forEach(ordersViewModelBuilder::getOrdersViewModel);
        viewModel = ordersViewModelBuilder.build();
    }
}

