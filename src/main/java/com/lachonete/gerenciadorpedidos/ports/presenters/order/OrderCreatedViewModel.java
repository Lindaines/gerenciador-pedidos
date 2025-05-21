package com.lachonete.gerenciadorpedidos.ports.presenters.order;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;
public class OrderCreatedViewModel {

    private String id;
    private Integer pickupCode;
    private String paymentId;
    @Value("${S3_BUCKET_PREFIX}")
    private String s3BucketPrefix="https://payments-lanchonete.s3.amazonaws.com/";
    private String qrCodeImageURL;

    public OrderCreatedViewModel(String id, Integer pickupCode, String paymentId) {
        this.id = id;
        this.pickupCode = pickupCode;
        this.paymentId = paymentId;
        this.qrCodeImageURL = getQrCodeImageURL();
    }

    public String getId() {
        return id;
    }

    public Integer getPickupCode() {
        return pickupCode;
    }

    public String getPaymentId() {
        return paymentId;
    }

    private String getQrCodeImageURL() {
        return this.s3BucketPrefix.concat(this.paymentId);
    }

}
