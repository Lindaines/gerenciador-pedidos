package com.lachonete.gerenciadorpedidos.presenters.order;

import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.CustomerResponse;

public class BaseOrderPresenter {

    protected BaseOrderPresenter() { }

    public static CustomerViewModel mapToCustomerViewModel(CustomerResponse responseModel) {
        return CustomerViewModel.builder()
                .build();
    }
}
