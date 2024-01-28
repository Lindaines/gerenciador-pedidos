package com.lachonete.gerenciadorpedidos.adapters.repositories;

import com.lachonete.gerenciadorpedidos.adapters.data.ProductData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends CrudRepository<ProductData, UUID> {
    List<ProductData> findAll();

}
