package com.lachonete.gerenciadorpedidos.adapters.out.repository.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderStatus;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenerationTime;
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

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

//    @Column(name = "pickup_code", nullable = false, unique = true, updatable = false, insertable = false)
//    @GeneratedValue(generator = "sequence", strategy = GenerationType.AUTO)
//    private Long pickUpCode;

    //@JsonManagedReferencez
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemEntity> items = new ArrayList<>();

}