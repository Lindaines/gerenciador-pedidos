package com.lachonete.gerenciadorpedidos.adapters;

import com.lachonete.gerenciadorpedidos.adapters.repositories.CustomerRepository;
import com.lachonete.gerenciadorpedidos.adapters.repositories.OrderRepository;
import com.lachonete.gerenciadorpedidos.adapters.repositories.PaymentRepository;
import com.lachonete.gerenciadorpedidos.adapters.repositories.ProductRepository;
import com.lachonete.gerenciadorpedidos.ports.database.*;

public class JpaDatabase implements Database {
    private ProductGateway productGateway;
    private CustomerGateway customerGateway;
    private OrderGateway orderGateway;
    private PaymentGateway paymentGateway;

    public JpaDatabase(ProductRepository productRepository, CustomerRepository customerRepository, OrderRepository orderRepository, PaymentRepository paymentRepository) {
        productGateway = new JpaProductGateway(productRepository);
        customerGateway = new JpaCustomerGateway(customerRepository);
        orderGateway = new JpaOrderGateway(orderRepository);
        paymentGateway = new JpaPaymentGateway(paymentRepository);
    }

    @Override
    public ProductGateway productGateway() {
        return productGateway;
    }

    @Override
    public CustomerGateway customerGateway() {
        return customerGateway;
    }

    @Override
    public OrderGateway orderGateway() {
        return orderGateway;
    }
    public PaymentGateway paymentGateway() {
        return paymentGateway;
    }
}
