package com.lachonete.gerenciadorpedidos.adapters.out.repository;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.CustomerEntity;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> findByCategory(String category);
}
