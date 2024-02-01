package com.lachonete.gerenciadorpedidos.usecases.customer.get;


import com.lachonete.gerenciadorpedidos.entities.Customer;
import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Image;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.CustomerResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductResponse;

public abstract class GetCustomerBase {
    protected GetCustomerBase() { }

    public static CustomerResponse makeCustomerResponse(Customer customer) {
        return new CustomerResponse(
               customer.getId().getValue(), customer.getName().toString(), customer.getCpf().toString(), customer.getEmail().toString());
    }

}