package com.lachonete.gerenciadorpedidos.ports.usescases.payment.update;

import java.util.UUID;

public interface UpdatePaymentInputBoundary {
    void execute(UpdatePaymentRequest request);
}
