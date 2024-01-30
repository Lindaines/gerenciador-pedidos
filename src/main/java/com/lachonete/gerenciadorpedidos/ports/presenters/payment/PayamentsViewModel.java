package com.lachonete.gerenciadorpedidos.ports.presenters.payment;

import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PayamentsViewModel {
    @Singular("getProductsViewModel") private List<PaymentStatusViewModel> products;
}
