package com.lachonete.gerenciadorpedidos.entities;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Money;
import com.lachonete.gerenciadorpedidos.entities.valueobject.OrderId;
import com.lachonete.gerenciadorpedidos.entities.valueobject.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public class Order extends AggregateRoot<OrderId> {
    private Money price;

    private List<OrderItem> items;
    private Integer pickupCode;
    private OrderStatus orderStatus;

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public Integer getPickupCode() {
        return pickupCode;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setPrice(Money price) {
        this.price = price;
    }
    public void setPickUpCodeInfo(Order order, Integer pickUpCode) {
        order.pickupCode = pickUpCode;
    }



    public void initializeOrder() {
        this.orderStatus = OrderStatus.CRIADO;
    }

    public void setPriceInfo(Order order) {
        List<BigDecimal> subTotal = order.getItems().stream().map(orderItem -> orderItem.getSubTotal().getAmount()).toList();
        BigDecimal total = subTotal.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setPrice(new Money(total));
    }

    public void updateStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }


    public static final class OrderBuilder {
        private OrderId orderId;

        private Money price;

        public OrderId getOrderId() {
            return orderId;
        }

        public Money getPrice() {
            return price;
        }

        public List<OrderItem> getItems() {
            return items;
        }

        public Integer getPickupCode() {
            return pickupCode;
        }

        public OrderStatus getOrderStatus() {
            return orderStatus;
        }

        private List<OrderItem> items;
        private Integer pickupCode;
        private OrderStatus orderStatus;

        private OrderBuilder() {
        }


        public static OrderBuilder anOrder() {
            return new OrderBuilder();
        }

        public OrderBuilder withId(OrderId id) {
            this.orderId = id;
            return this;
        }

        public OrderBuilder withPrice(Money price) {
            this.price = price;
            return this;
        }

        public OrderBuilder withOrderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public OrderBuilder withItems(List<OrderItem> items) {
            this.items = items;
            return this;
        }

        public OrderBuilder withPickupCode(Integer pickupCode) {
            this.pickupCode = pickupCode;
            return this;
        }

        public Order build() {
            Order order = new Order();
            order.setId(this.orderId);
            order.price = this.price;
            order.items = this.items;
            order.pickupCode = this.pickupCode;
            order.orderStatus = this.orderStatus;
            return order;
        }
    }

    // to do: implement domain validations
}
