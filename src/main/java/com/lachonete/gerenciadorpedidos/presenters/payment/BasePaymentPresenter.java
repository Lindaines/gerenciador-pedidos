package com.lachonete.gerenciadorpedidos.presenters.payment;

import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerViewModel;
import com.lachonete.gerenciadorpedidos.ports.presenters.payment.PaymentStatusViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.CustomerResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.get.PaymentResponse;

public class BasePaymentPresenter {

    protected BasePaymentPresenter() { }

    public static PaymentStatusViewModel mapToPaymentStatusViewModel(PaymentResponse responseModel) {
        return PaymentStatusViewModel.builder()
                .id(responseModel.getId())
                .status(responseModel.getPaymentStatus())
                .build();
    }
}
