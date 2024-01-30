package com.lachonete.gerenciadorpedidos.usecases.payment.add;


import com.lachonete.gerenciadorpedidos.entities.Payment;
import com.lachonete.gerenciadorpedidos.entities.valueobject.CustomerId;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Money;
import com.lachonete.gerenciadorpedidos.entities.valueobject.OrderId;
import com.lachonete.gerenciadorpedidos.entities.valueobject.PaymentStatus;
import com.lachonete.gerenciadorpedidos.ports.database.PaymentGateway;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.add.AddPaymentInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.add.AddPaymentRequest;

import java.util.UUID;

public class AddPayment implements AddPaymentInputBoundary {

    private final PaymentGateway paymentGateway;

    public AddPayment(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }
    @Override
    public UUID execute(AddPaymentRequest request) {
        return addPayment(request);
    }

    private UUID addPayment(AddPaymentRequest request) {
        Payment payment = Payment.builder()
                .orderId(new OrderId(request.getOrderId()))
                .customerId(new CustomerId(request.getCustomerId()))
                .price(new Money(request.getPrice()))
                .paymentStatus(PaymentStatus.AGUARDANDO)
                .build();
        return paymentGateway.add(payment);
    }
}
