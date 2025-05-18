package com.lachonete.gerenciadorpedidos.infra;

import com.lachonete.gerenciadorpedidos.entities.Order;

import java.math.BigDecimal;

public class PaymentRequest {
    public PaymentRequest(Order order) {
        this.order_id = order.getId().getValue().toString();
        this.company = System.getenv("PAYMENT_COMPANY");
        this.cnpj = System.getenv("PAYMENT_CNPJ");
        this.city = System.getenv("PAYMENT_CITY");
        this.store = System.getenv("PAYMENT_STORE");
        this.amount = order.getPrice().getAmount();
    }

    private String order_id;
    private String company;
    private String cnpj;
    private String city;
    private String store;
    private BigDecimal amount;


    public String getOrder_id() {
        return order_id;
    }

    public String getCompany() {
        return company;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getCity() {
        return city;
    }

    public String getStore() {
        return store;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}