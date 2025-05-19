package com.lachonete.gerenciadorpedidos.api.endpoints.payment.get;


import com.lachonete.gerenciadorpedidos.api.endpoints.BaseEndpoint;
import com.lachonete.gerenciadorpedidos.ports.presenters.payment.PaymentStatusOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.get.GetPaymentByIdRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.get.GetPaymentInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.GetProductByIdRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.GetProductInputBoundary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
public class GetPaymentStatusByIdEndpoint implements BaseEndpoint {
    private final GetPaymentInputBoundary useCase;
    private final PaymentStatusOutputBoundary presenter;

    public GetPaymentStatusByIdEndpoint(GetPaymentInputBoundary useCase, PaymentStatusOutputBoundary presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity execute(@PathVariable final String paymentId) {
        useCase.execute(GetPaymentByIdRequest.builder().id(paymentId).build());
        return ResponseEntity.ok(presenter.getViewModel());
    }
}
