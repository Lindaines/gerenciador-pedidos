package com.lachonete.gerenciadorpedidos.usecases.customer.get;


import com.lachonete.gerenciadorpedidos.entities.Customer;
import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.ports.database.CustomerGateway;
import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.GetCustomerByIdRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.GetCustomerInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.GetProductByIdRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.GetProductInputBoundary;

public class GetCustomerById extends GetCustomerBase implements GetCustomerInputBoundary {
    private final CustomerOutputBoundary presenter;
    private final CustomerGateway customerGateway;

    public GetCustomerById(CustomerOutputBoundary presenter, CustomerGateway productGateway) {
        this.presenter = presenter;
        this.customerGateway = productGateway;
    }

    public void execute(GetCustomerByIdRequest request) {
        Customer customer = customerGateway.getById(request.getId());
        if (customer == null)
            throw new CustomerGateway.CustomerNotFoundException();
        presenter.present(makeCustomerResponse(customer));
    }

}