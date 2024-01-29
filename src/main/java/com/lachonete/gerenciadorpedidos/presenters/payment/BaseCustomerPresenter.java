package com.lachonete.gerenciadorpedidos.presenters.payment;

import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.CustomerResponse;

public class BaseCustomerPresenter {

    protected BaseCustomerPresenter() { }

    public static CustomerViewModel mapToCustomerViewModel(CustomerResponse responseModel) {
        return CustomerViewModel.builder()
                .id(responseModel.getId())
                .name(responseModel.getName())
                .cpf(responseModel.getCpf())
                .email(responseModel.getEmail())
                .build();
    }
}
