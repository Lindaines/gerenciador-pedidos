package com.lachonete.gerenciadorpedidos.presenters.order;

import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerViewModel;
import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrderItemViewModel;
import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrderViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.CustomerResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrderItemResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrderResponse;

public class BaseOrderPresenter {

    protected BaseOrderPresenter() { }

    public static OrderItemViewModel mapToOrderItemViewModel(OrderItemResponse orderItemResponse) {
        return OrderItemViewModel.builder()
                .id(orderItemResponse.getId())
                .quantity(orderItemResponse.getQuantity())
                .productId(orderItemResponse.getProductId())
                .subTotal(orderItemResponse.getSubTotal())
                .build();
    }

    public static OrderViewModel mapToOrderViewModel(OrderResponse responseModel) {
        return OrderViewModel.builder()
                .id(responseModel.getId())
                .price(responseModel.getPrice())
                .pickUpCode(responseModel.getPickUpCode())
                .items(responseModel.getItems().stream().map(BaseOrderPresenter::mapToOrderItemViewModel).toList())
                .orderStatus(responseModel.getOrderStatus())
                .build();
    }
}
