package com.lachonete.gerenciadorpedidos.adapters.out.repository.mapper;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.CustomerEntity;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.ProductEntity;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Customer;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.Cpf;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.CustomerId;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.EmailAddress;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.PersonName;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityMapper {
    public ProductEntity toProductEntity(Product product){
        var images = product.getImages().stream().map(image -> image.urlPath).toList();
        return ProductEntity.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice().getAmount())
                .category(product.getCategory().toString())
                .images(images).build();
    }

//    public Customer toCustomer(CustomerEntity customerEntity){
//        var cpf = new Cpf(customerEntity.getCpf());
//        var email = new EmailAddress(customerEntity.getEmail());
//        var name = new PersonName(customerEntity.getName());
//        var id = new CustomerId(customerEntity.getId());
//
//        return new Customer(id, cpf, email, name);
//    }
}
