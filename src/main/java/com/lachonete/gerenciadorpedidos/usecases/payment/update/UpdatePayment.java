package com.lachonete.gerenciadorpedidos.usecases.payment.update;


import com.lachonete.gerenciadorpedidos.entities.Payment;
import com.lachonete.gerenciadorpedidos.ports.database.PaymentGateway;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.update.UpdatePaymentInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.update.UpdatePaymentRequest;

import java.util.UUID;

public class UpdatePayment implements UpdatePaymentInputBoundary {

    private final PaymentGateway paymentGateway;

    public UpdatePayment(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    @Override
    public void execute(UpdatePaymentRequest request) {
        validatePayment(request.getId());
        partialUpdate(request);
    }
    public void validatePayment(UUID id){
        Payment payment = paymentGateway.getById(id);
        if (payment==null){
            throw new PaymentGateway.PaymentNotFoundException();
        }
        // to do - implement more validations
    }

    private void partialUpdate(UpdatePaymentRequest request) {
        paymentGateway.updateStatus(request.getId(), request.getStatus());
    }
}
