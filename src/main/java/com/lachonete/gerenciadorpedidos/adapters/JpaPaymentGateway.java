package com.lachonete.gerenciadorpedidos.adapters;


import com.lachonete.gerenciadorpedidos.adapters.data.PaymentData;
import com.lachonete.gerenciadorpedidos.adapters.repositories.PaymentRepository;
import com.lachonete.gerenciadorpedidos.entities.Payment;
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
    public List<Payment> getAll() {
        return paymentRepository.findAll().stream().map(this::mapToPayment).toList();
    }

    private Payment mapToPayment(PaymentData paymentData) {
       return Payment.builder().build();
    }

    private PaymentData mapToPaymentData(Payment payment) {

        return PaymentData.builder().build();
    }

    public UUID add(Payment payment) {
        var paymentData = this.mapToPaymentData(payment);
        PaymentData paymentSaved = paymentRepository.save(paymentData);
        return paymentSaved.getId();
    }

    @Override
    public Payment getById(UUID id) {
        return null;
    }


}
