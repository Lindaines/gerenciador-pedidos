package com.lachonete.gerenciadorpedidos.adapters.data;

import com.lachonete.gerenciadorpedidos.entities.valueobject.PaymentStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
@Entity
public class PaymentData {
    @Id
//    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
//    @GeneratedValue(generator = "UUIDGenerator")
    private String id;
    private UUID customerId;
    private UUID orderId;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private ZonedDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentData that = (PaymentData) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}