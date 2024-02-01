package com.lachonete.gerenciadorpedidos.usecases.payment.get;


import com.lachonete.gerenciadorpedidos.entities.Payment;
import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Image;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.get.PaymentResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductResponse;

public abstract class GetPaymentBase {
    protected GetPaymentBase() { }

    public static PaymentResponse makePaymentResponse(Payment payment) {
       return PaymentResponse.builder()
               .id(payment.getId().getValue())
               .orderId(payment.getOrderId().getValue())
               .customerId(payment.getCustomerId().getValue())
               .paymentStatus(payment.getPaymentStatus())
               .price(payment.getPrice().getAmount())
               .build();
    }

    public static PaymentResponse makePaymentStatusResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId().getValue())
                .paymentStatus(payment.getPaymentStatus())
                .build();
    }

}