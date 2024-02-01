package com.lachonete.gerenciadorpedidos.presenters.payment;

import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerViewModel;
import com.lachonete.gerenciadorpedidos.ports.presenters.payment.PaymentStatusOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.payment.PaymentStatusViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.CustomerResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.get.PaymentResponse;

public class PaymentStatusPresenter extends BasePaymentPresenter implements PaymentStatusOutputBoundary {

    private PaymentStatusViewModel viewModel;
    @Override
    public PaymentStatusViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void present(PaymentResponse responseModel) {
        viewModel = mapToPaymentStatusViewModel(responseModel);
    }
}
