package com.lachonete.gerenciadorpedidos.presenters.order;

import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.CustomerResponse;

public class OrderPresenter extends BaseOrderPresenter implements CustomerOutputBoundary {

    private CustomerViewModel viewModel;
    @Override
    public CustomerViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void present(CustomerResponse responseModel) {
        viewModel = mapToCustomerViewModel(responseModel);
    }
}
