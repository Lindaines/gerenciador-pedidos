package com.lachonete.gerenciadorpedidos.adapters.in.controller.mapper;

import com.lachonete.gerenciadorpedidos.adapters.in.controller.request.CustomerRequest;
import com.lachonete.gerenciadorpedidos.adapters.in.controller.response.CustomerResponse;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Customer;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.Cpf;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.EmailAddress;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.PersonName;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer toCustomer(CustomerRequest customerRequest){
        var cpf = new Cpf(customerRequest.getCpf());
        var email = new EmailAddress(customerRequest.getEmail());
        var name = new PersonName(customerRequest.getName());
        return new Customer(cpf, email, name);
    }

    public CustomerResponse toCustomerResponse (Customer customer){
        return new CustomerResponse(customer);
    }
}
