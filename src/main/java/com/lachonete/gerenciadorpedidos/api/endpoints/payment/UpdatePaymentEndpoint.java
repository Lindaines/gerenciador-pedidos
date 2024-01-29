package com.lachonete.gerenciadorpedidos.api.endpoints.payment;


import com.lachonete.gerenciadorpedidos.api.endpoints.BaseEndpoint;
import com.lachonete.gerenciadorpedidos.api.endpoints.product.updateProduct.UpdateProductRequest;
import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrderCreatedOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.CheckoutOrderInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.CheckoutOrderRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.OrderItemRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.update.UpdatePaymentInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.update.UpdatePaymentRequest;
import com.lachonete.gerenciadorpedidos.usecases.payment.UpdatePayment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.text.MessageFormat;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
public class UpdatePaymentEndpoint implements BaseEndpoint {
    private final UpdatePaymentInputBoundary useCase;

    public UpdatePaymentEndpoint(UpdatePaymentInputBoundary useCase) {
        this.useCase = useCase;
    }

    @PatchMapping("/{paymentId}")
    public ResponseEntity execute(@PathVariable final UUID paymentId, @Valid @RequestBody UpdatePaymentStatusRequest request){
        UpdatePaymentRequest updateRequest = UpdatePaymentRequest.builder().id(paymentId).status(request.getPaymentStatus()).build();
        useCase.execute(updateRequest);
        return ResponseEntity.ok().build();

    }
}
