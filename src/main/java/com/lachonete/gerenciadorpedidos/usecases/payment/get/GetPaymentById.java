package com.lachonete.gerenciadorpedidos.usecases.payment.get;


import com.lachonete.gerenciadorpedidos.entities.PaymentDeprecated;
import com.lachonete.gerenciadorpedidos.ports.database.PaymentGateway;
import com.lachonete.gerenciadorpedidos.ports.presenters.payment.PaymentStatusOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.get.GetPaymentByIdRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.get.GetPaymentInputBoundary;

public class GetPaymentById extends GetPaymentBase implements GetPaymentInputBoundary {
    private final PaymentStatusOutputBoundary presenter;
    private final PaymentGateway paymentGateway;

    public GetPaymentById(PaymentStatusOutputBoundary presenter, PaymentGateway paymentGateway) {
        this.presenter = presenter;
        this.paymentGateway = paymentGateway;
    }


    @Override
    public void execute(GetPaymentByIdRequest request) {
        PaymentDeprecated payment = paymentGateway.getById(request.getId());
        presenter.present(makePaymentStatusResponse(payment));
    }
}