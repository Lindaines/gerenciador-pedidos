package com.lachonete.gerenciadorpedidos.api.endpoints.payment.update;


import com.lachonete.gerenciadorpedidos.api.endpoints.BaseEndpoint;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.update.UpdatePaymentInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.update.UpdatePaymentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
public class UpdatePaymentEndpoint implements BaseEndpoint {
    private final UpdatePaymentInputBoundary useCase;

    public UpdatePaymentEndpoint(UpdatePaymentInputBoundary useCase) {
        this.useCase = useCase;
    }

    @PatchMapping("/{paymentId}")
    public ResponseEntity execute(@PathVariable final String paymentId, @Valid @RequestBody UpdatePaymentStatusRequest request){
        UpdatePaymentRequest updateRequest = UpdatePaymentRequest.builder().id(paymentId).status(request.getPaymentStatus()).build();
        useCase.execute(updateRequest);
        return ResponseEntity.ok().build();

    }
}
