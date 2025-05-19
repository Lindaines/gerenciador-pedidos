package com.lachonete.gerenciadorpedidos.usecases.payment.get;


import com.lachonete.gerenciadorpedidos.entities.PaymentDeprecated;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.get.PaymentResponse;

public abstract class GetPaymentBase {
    protected GetPaymentBase() { }

    public static PaymentResponse makePaymentResponse(PaymentDeprecated payment) {
       return PaymentResponse.builder()
               .id(payment.getId().getValue())
               .orderId(payment.getOrderId().getValue())
               .customerId(payment.getCustomerId().getValue())
               .paymentStatus(payment.getPaymentStatus())
               .price(payment.getPrice().getAmount())
               .build();
    }

    public static PaymentResponse makePaymentStatusResponse(PaymentDeprecated payment) {
        return PaymentResponse.builder()
                .id(payment.getId().toString())
                .paymentStatus(payment.getPaymentStatus())
                .build();
    }

}