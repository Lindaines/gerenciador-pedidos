package com.lachonete.gerenciadorpedidos.usecases.customer.add;


import com.lachonete.gerenciadorpedidos.entities.Customer;
import com.lachonete.gerenciadorpedidos.entities.valueobject.*;
import com.lachonete.gerenciadorpedidos.ports.database.CustomerGateway;
import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerCreatedOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.add.AddCustomerInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.add.AddCustomerRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.add.NewCustomerResponse;

import java.util.UUID;

public class AddCustomer implements AddCustomerInputBoundary {
    private final CustomerCreatedOutputBoundary presenter;
    private final CustomerGateway customerGateway;

    public AddCustomer(CustomerCreatedOutputBoundary presenter, CustomerGateway customerGateway) {
        this.presenter = presenter;
        this.customerGateway = customerGateway;
    }

    public void execute(AddCustomerRequest request) {
        validateCustomer(request);
        UUID id = addCustomer(request);

        NewCustomerResponse responseModel = new NewCustomerResponse(id);
        presenter.present(responseModel);
    }

    private void validateCustomer(final AddCustomerRequest request) {
        if (customerAlreadyExists(request))
            throw new CustomerGateway.CustomerAlreadyByNameExistsException();
    }

    private boolean customerAlreadyExists(final AddCustomerRequest request) {
        // To do: Validation
        return false;
    }

    private UUID addCustomer(AddCustomerRequest request) {
        var cpf = new Cpf(request.getCpf());
        var email = new EmailAddress(request.getEmail());
        var name = new PersonName(request.getName());
        var customer = new Customer(cpf, email, name);
        return customerGateway.add(customer).getValue();

    }
}
