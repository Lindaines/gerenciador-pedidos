package com.lachonete.gerenciadorpedidos.application.core.domain.entity;

import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.CustomerId;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.Money;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderId;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderStatus;

import java.util.List;

public class Order extends AggregateRoot<OrderId>{
    private Money price;
    private List<OrderItem> items;
    private Long pickupCode;
    private OrderStatus orderStatus;


    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public Long getPickupCode() {
        return pickupCode;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void initializeOrder(){
        this.orderStatus = OrderStatus.CRIADO;
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

        public Long getPickupCode() {
            return pickupCode;
        }

        public OrderStatus getOrderStatus() {
            return orderStatus;
        }

        private List<OrderItem> items;
        private Long pickupCode;
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

        public OrderBuilder withPickupCode(Long pickupCode) {
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
