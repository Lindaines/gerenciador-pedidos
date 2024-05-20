package com.lachonete.gerenciadorpedidos.infra;

import com.lachonete.gerenciadorpedidos.entities.Order;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class PaymentRequest {
    @JsonProperty("value")
    private BigDecimal value;
    @JsonProperty("company")
    private final String company = "Lanchonete da Mata";
    @JsonProperty("cnpj")
    private final String cnpj = "05324399000180";
    @JsonProperty("city")
    private final String city = "Patos de Minas";
    @JsonProperty("store")
    private final String store = "Loja 01";

    public PaymentRequest(Order order) {
        this.value = order.getPrice().getAmount();
    }
}