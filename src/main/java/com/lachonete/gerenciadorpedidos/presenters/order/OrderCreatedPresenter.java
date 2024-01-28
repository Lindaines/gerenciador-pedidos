package com.lachonete.gerenciadorpedidos.presenters.order;

import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrderCreatedOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrderCreatedViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.CheckoutOrderResponse;

public class OrderCreatedPresenter implements OrderCreatedOutputBoundary {
    private OrderCreatedViewModel viewModel;

    public OrderCreatedViewModel getViewModel() {
        return viewModel;
    }


    public void present(CheckoutOrderResponse responseModel) {
        viewModel = new OrderCreatedViewModel(responseModel.getId().toString(), responseModel.getPickupCode());
    }
}
