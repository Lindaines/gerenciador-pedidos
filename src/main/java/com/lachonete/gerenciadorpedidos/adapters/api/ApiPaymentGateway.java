package com.lachonete.gerenciadorpedidos.adapters.api;

import com.lachonete.gerenciadorpedidos.entities.Order;
import com.lachonete.gerenciadorpedidos.entities.Payment;
import com.lachonete.gerenciadorpedidos.infra.PaymentRequest;
import com.lachonete.gerenciadorpedidos.infra.PaymentResponse;
import com.lachonete.gerenciadorpedidos.ports.api.PaymentGateway;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;


public class ApiPaymentGateway implements PaymentGateway {

    private final RestTemplate restTemplate;
    @Value("${PAYMENT_API_ENDPOINT_URL}")
    private String paymentServiceURL;

    public ApiPaymentGateway(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Payment createPayment(Order order) {
        PaymentRequest request = new PaymentRequest(order);
        try {
            PaymentResponse response = restTemplate.postForObject(this.paymentServiceURL, request, PaymentResponse.class);
            return response.toPayment(response);
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }
}
