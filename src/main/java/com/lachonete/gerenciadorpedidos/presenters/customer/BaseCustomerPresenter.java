package com.lachonete.gerenciadorpedidos.presenters.customer;

import com.lachonete.gerenciadorpedidos.entities.Customer;
import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerViewModel;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductResponse;

public class BaseCustomerPresenter {

    protected BaseCustomerPresenter() { }

    public static CustomerViewModel mapToCustomerViewModel(ProductResponse responseModel) {
        return CustomerViewModel.builder().build();
    }
}
