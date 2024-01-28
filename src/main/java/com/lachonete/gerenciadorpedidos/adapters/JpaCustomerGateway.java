package com.lachonete.gerenciadorpedidos.adapters;

import com.lachonete.gerenciadorpedidos.adapters.data.CustomerData;
import com.lachonete.gerenciadorpedidos.adapters.data.ProductData;
import com.lachonete.gerenciadorpedidos.adapters.repositories.CustomerRepository;
import com.lachonete.gerenciadorpedidos.adapters.repositories.ProductRepository;
import com.lachonete.gerenciadorpedidos.entities.Customer;
import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.entities.valueobject.*;
import com.lachonete.gerenciadorpedidos.ports.database.CustomerGateway;
import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JpaCustomerGateway implements CustomerGateway {
    private final CustomerRepository customerRepository;

    public JpaCustomerGateway(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll().stream().map(this::mapToCustomer).toList();
    }

    private Customer mapToCustomer(CustomerData customerData) {
        var cpf = new Cpf(customerData.getCpf());
        var email = new EmailAddress(customerData.getEmail());
        var name = new PersonName(customerData.getName());
        var id = new CustomerId(customerData.getId());

        return new Customer(id, cpf, email, name);
    }

    public CustomerId add(Customer customer) {
        UUID id = UUID.randomUUID();
        CustomerData customerData =  new CustomerData(id,
                customer.getCpf().toString(),
                customer.getName().toString(),
                customer.getEmail().toString());
        customerRepository.save(customerData);
        return new CustomerId(id);
    }

    @Override
    public Customer getById(UUID id) {
        return customerRepository.findById(id)
          .map(this::mapToCustomer)
                .orElse(null);
    }

    @Override
    public void update(Customer customer) {
        CustomerData customerData =  new CustomerData(
                customer.getCpf().toString(),
                customer.getName().toString(),
                customer.getEmail().toString());
        customerRepository.save(customerData);    }

    @Override
    public void delete(UUID id) {
        customerRepository.deleteById(id);
    }

}
