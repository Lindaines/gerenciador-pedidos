package com.lachonete.gerenciadorpedidos.infra;

import com.lachonete.gerenciadorpedidos.entities.Payment;
import lombok.Getter;

public class PaymentResponse {

    public String getPayment_id() {
        return payment_id;
    }

    public String getPix_qr_code_url() {
        return pix_qr_code_url;
    }

    public String getStatus() {
        return status;
    }

    private String payment_id;
    private String pix_qr_code_url;
    private String status;

    public Payment toPayment(PaymentResponse response){
        return new Payment(response.getPayment_id());
    }
}
