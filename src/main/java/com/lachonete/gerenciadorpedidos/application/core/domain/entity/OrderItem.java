package com.lachonete.gerenciadorpedidos.application.core.domain.entity;

import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.Money;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderId;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderItemId;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductId;

public class OrderItem extends BaseEntity<OrderItemId> {
    private OrderId orderId;
    private ProductId productId;
    private  int quantity;
    private  Money subTotal;

    public OrderId getOrderId() {
        return orderId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getSubTotal() {
        return subTotal;
    }

    void initializeOrderItem(OrderId orderId, OrderItemId orderItemId) {
        this.orderId = orderId;
        super.setId(orderItemId);
    }


    public static final class OrderItemBuilder {
        private OrderItemId id;
        private OrderId orderId;
        private ProductId productId;
        private int quantity;
        private Money subTotal;
        public OrderItemId getId() {
            return id;
        }

        public OrderId getOrderId() {
            return orderId;
        }

        public ProductId getProductId() {
            return productId;
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

        public OrderItemBuilder withProductId(ProductId productId) {
            this.productId = productId;
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
            orderItem.productId = this.productId;
            return orderItem;
        }
    }
}
