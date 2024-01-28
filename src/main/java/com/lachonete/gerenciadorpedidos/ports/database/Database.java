package com.lachonete.gerenciadorpedidos.ports.database;

public interface Database {
    ProductGateway productGateway();
    CustomerGateway customerGateway();
}
