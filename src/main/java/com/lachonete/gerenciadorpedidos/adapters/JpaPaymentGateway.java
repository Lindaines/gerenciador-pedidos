package com.lachonete.gerenciadorpedidos.adapters;


import com.lachonete.gerenciadorpedidos.adapters.data.PaymentData;
import com.lachonete.gerenciadorpedidos.adapters.repositories.PaymentRepository;
import com.lachonete.gerenciadorpedidos.entities.PaymentDeprecated;
import com.lachonete.gerenciadorpedidos.entities.valueobject.*;
import com.lachonete.gerenciadorpedidos.ports.database.PaymentGateway;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
public class JpaPaymentGateway implements PaymentGateway {
    private final PaymentRepository paymentRepository;

    public JpaPaymentGateway(final PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    @Override
    public List<PaymentDeprecated> getAll() {
        return paymentRepository.findAll().stream().map(this::mapToPayment).toList();
    }

    private PaymentDeprecated mapToPayment(PaymentData paymentData) {
       return PaymentDeprecated.builder()
               .paymentId(new PaymentId(paymentData.getId()))
               .orderId(new OrderId(paymentData.getOrderId()))
               .price(new Money(paymentData.getPrice()))
               .customerId(new CustomerId(paymentData.getCustomerId()))
               .paymentStatus(paymentData.getStatus())
               .build();
    }

    private PaymentData mapToPaymentData(PaymentDeprecated payment) {
        return PaymentData.builder()
                .orderId(payment.getOrderId().getValue())
                .customerId(payment.getCustomerId().getValue())
                .price(payment.getPrice().getAmount())
                .status(payment.getPaymentStatus())
                .build();
    }

    public String add(PaymentDeprecated payment) {
        var paymentData = this.mapToPaymentData(payment);
        PaymentData paymentSaved = paymentRepository.save(paymentData);
        return paymentSaved.getId();
    }

    @Override
    public PaymentDeprecated getById(String id) {
        return paymentRepository.findById(id)
                .map(this::mapToPayment)
                .orElse(null);
    }

    @Override
    public void updateStatus(String id, PaymentStatus paymentStatus) {
        PaymentDeprecated payment = getById(id);
        payment.updateStatus(paymentStatus);
        var paymentData = this.mapToPaymentData(payment);
        paymentData.setId(payment.getId().getValue());
        paymentRepository.save(paymentData);
    }



}
