package com.lachonete.gerenciadorpedidos.adapters;

import com.lachonete.gerenciadorpedidos.adapters.repositories.CustomerRepository;
import com.lachonete.gerenciadorpedidos.adapters.repositories.OrderRepository;
import com.lachonete.gerenciadorpedidos.adapters.repositories.ProductRepository;
import com.lachonete.gerenciadorpedidos.ports.database.CustomerGateway;
import com.lachonete.gerenciadorpedidos.ports.database.Database;
import com.lachonete.gerenciadorpedidos.ports.database.OrderGateway;
import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;

public class JpaDatabase implements Database {
    private ProductGateway productGateway;
    private CustomerGateway customerGateway;
    private OrderGateway orderGateway;

    public JpaDatabase(ProductRepository productRepository, CustomerRepository customerRepository, OrderRepository orderRepository) {
        productGateway = new JpaProductGateway(productRepository);
        customerGateway = new JpaCustomerGateway(customerRepository);
        orderGateway = new JpaOrderGateway(orderRepository);
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
}
