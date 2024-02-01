package com.lachonete.gerenciadorpedidos.presenters.order;

import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrderOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrderViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrderResponse;

public class OrderPresenter extends BaseOrderPresenter implements OrderOutputBoundary {

    private OrderViewModel viewModel;


    @Override
    public OrderViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void present(OrderResponse responseModel) {
        viewModel = mapToOrderViewModel(responseModel);
    }
}
