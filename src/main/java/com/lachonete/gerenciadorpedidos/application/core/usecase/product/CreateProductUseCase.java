package com.lachonete.gerenciadorpedidos.application.core.usecase.product;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Customer;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.ports.in.customer.CreateCustomerInputPort;
import com.lachonete.gerenciadorpedidos.application.ports.in.product.CreateProductInputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.customer.CreateCustomerOutputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.product.CreateProductOutputPort;


public class CreateProductUseCase implements CreateProductInputPort {
    private final CreateProductOutputPort createCustomerOutputPort;

    public CreateProductUseCase(CreateProductOutputPort createProductOutputPort) {
        this.createCustomerOutputPort = createProductOutputPort;
    }


    @Override
    public void create(Product product) {
        createCustomerOutputPort.create(product);
    }
}
