package com.lachonete.gerenciadorpedidos.adapters.out.repository.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(OrderItemEntityId.class)
@Table(name = "order_items")
@Entity
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID")
    @JsonIgnore
    private OrderEntity order;

//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name="PRODUCT_ID", nullable = false, updatable = false, insertable = false)
//   // @JoinColumn(name="PRODUCT_ID", nullable = false, updatable = false, insertable = false)
//    private ProductEntity product;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "PRODUCT_ID")
//    private ProductEntity product;

    private UUID productId;

    private BigDecimal subTotal;
    private BigDecimal price;

    private int  quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemEntity that = (OrderItemEntity) o;
        return id.equals(that.id) && order.equals(that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order);
    }
}