package com.lachonete.gerenciadorpedidos.presenters.payment;

import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerCreatedOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerCreatedViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.add.NewCustomerResponse;

public class CustomerCreatedPresenter implements CustomerCreatedOutputBoundary {
    private CustomerCreatedViewModel viewModel;

    public CustomerCreatedViewModel getViewModel() {
        return viewModel;
    }

    public void present(NewCustomerResponse responseModel) {
        viewModel = new CustomerCreatedViewModel(responseModel.getId().toString());
    }
}
