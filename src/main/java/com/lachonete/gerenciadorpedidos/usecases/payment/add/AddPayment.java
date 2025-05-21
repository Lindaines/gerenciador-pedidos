package com.lachonete.gerenciadorpedidos.usecases.payment.add;


import com.lachonete.gerenciadorpedidos.entities.Payment;
import com.lachonete.gerenciadorpedidos.ports.api.PaymentGateway;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.add.AddPaymentInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.add.AddPaymentRequest;

public class AddPayment implements AddPaymentInputBoundary {

    private final PaymentGateway paymentGateway;

    public AddPayment(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    @Override
    public String execute(AddPaymentRequest request) {
        Payment payment = paymentGateway.createPayment(request.getOrder());
        return payment.getPaymentId();
    }
}
