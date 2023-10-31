package com.lachonete.gerenciadorpedidos.application.core.domain.entity;

import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.Money;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderId;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderItemId;

public class OrderItem extends BaseEntity<OrderItemId> {
    private OrderId orderId;

    private Product product;
    private  int quantity;
    private  Money subTotal;
    public OrderId getOrderId() {
        return orderId;
    }

    public void setOrderId(OrderId orderId) {
        this.orderId = orderId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getSubTotal() {
        return subTotal;
    }

    public void setSubTotal (Product product, int quantity){
        this.subTotal = product.getPrice().multiply(quantity);
    }

    void initializeOrderItem(OrderId orderId, OrderItemId orderItemId) {
        this.orderId = orderId;
        super.setId(orderItemId);
    }


    public static final class OrderItemBuilder {
        private OrderItemId id;
        private OrderId orderId;
        private Product product;
        private int quantity;
        private Money subTotal;
        public OrderItemId getId() {
            return id;
        }

        public OrderId getOrderId() {
            return orderId;
        }

        public Product getProductId() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }


        public Money getSubTotal() {
            return subTotal;
        }



        private OrderItemBuilder() {
        }

        public static OrderItemBuilder anOrderItem() {
            return new OrderItemBuilder();
        }

        public OrderItemBuilder withId(OrderItemId id) {
            this.id = id;
            return this;
        }

        public OrderItemBuilder withOrderId(OrderId orderId) {
            this.orderId = orderId;
            return this;
        }

        public OrderItemBuilder withProduct(Product product) {
            this.product = product;
            return this;
        }

        public OrderItemBuilder withQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderItemBuilder withSubTotal(Money subTotal) {
            this.subTotal = subTotal;
            return this;
        }

        public OrderItem build() {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(id);
            orderItem.subTotal = this.subTotal;
            orderItem.quantity = this.quantity;
            orderItem.orderId = this.orderId;
            orderItem.product = this.product;
            return orderItem;
        }
    }
}
