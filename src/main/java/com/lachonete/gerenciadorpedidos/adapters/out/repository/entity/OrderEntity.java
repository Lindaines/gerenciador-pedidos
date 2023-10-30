package com.lachonete.gerenciadorpedidos.adapters.out.repository.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@Entity
public class OrderEntity {
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    private BigDecimal price;
    @Transient
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Long pickUpCode;

    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.MERGE)
    private List<OrderItemEntity> items;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}