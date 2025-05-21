package com.lachonete.gerenciadorpedidos.ports.usescases.payment.add;

import java.util.UUID;

public interface AddPaymentInputBoundary {
    String execute(AddPaymentRequest request);
}
