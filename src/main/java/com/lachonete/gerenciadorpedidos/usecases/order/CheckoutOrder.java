package com.lachonete.gerenciadorpedidos.usecases.order;


import com.lachonete.gerenciadorpedidos.entities.Order;
import com.lachonete.gerenciadorpedidos.entities.OrderItem;
import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.entities.valueobject.*;
import com.lachonete.gerenciadorpedidos.infra.PaymentRequest;
import com.lachonete.gerenciadorpedidos.ports.database.OrderGateway;
import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrderCreatedOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.add.CheckoutOrderInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.add.CheckoutOrderRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.add.CheckoutOrderResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.add.AddPaymentInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.add.AddPaymentRequest;
import com.lachonete.gerenciadorpedidos.usecases.exception.PaymentServiceException;

import java.text.MessageFormat;


public class CheckoutOrder implements CheckoutOrderInputBoundary {
    private final OrderCreatedOutputBoundary presenter;
    private final OrderGateway orderGateway;
    private final ProductGateway productGateway;
    private final AddPaymentInputBoundary addPaymentUseCase;

    public CheckoutOrder(OrderCreatedOutputBoundary presenter, OrderGateway orderGateway, ProductGateway productGateway, AddPaymentInputBoundary addPaymentInputBoundary) {
        this.presenter = presenter;
        this.orderGateway = orderGateway;
        this.productGateway = productGateway;
        this.addPaymentUseCase = addPaymentInputBoundary;
    }

    @Override
    public void execute(CheckoutOrderRequest request) {
        var order = mapToOrder(request);
        order.setPriceInfo(order);
        validateOrderItemInfo(order);
        order.initializeOrder();
        var orderSaved = addOrder(order);
        var paymentId = addPaymentUseCase.execute(new AddPaymentRequest(orderSaved));
        order.setPaymentId(paymentId);
        order.updateStatus(OrderStatus.AGUARDANDO_PAGAMENTO);
        addOrder(order);
        CheckoutOrderResponse responseModel = new CheckoutOrderResponse(orderSaved.getId().getValue(), orderSaved.getPickupCode(), paymentId);
        presenter.present(responseModel);
    }

    private Order mapToOrder(CheckoutOrderRequest request) {
        var orderItems = request.getItems().stream().map(orderItemRequest -> {
            var product = new Product(new ProductId(orderItemRequest.getProductId()));
            var price = new Money(orderItemRequest.getPrice());
            var subTotal = new Money(orderItemRequest.getSubtotal());
            return OrderItem.OrderItemBuilder.anOrderItem()
                    .withProduct(product)
                    .withQuantity(orderItemRequest.getQuantity())
                    .withSubTotal(subTotal)
                    .withPrice(price)
                    .build();
        }).toList();
        return Order.OrderBuilder.anOrder().withItems(orderItems).build();
    }

    private Order addOrder(Order order) {
        return orderGateway.add(order);
    }

    public void validateOrderItemInfo(Order order) {
        order.getItems().forEach(orderItem -> {
            var product = productGateway.getById(orderItem.getProduct().getId().getValue());
            if (product == null) {
                throw new ProductGateway.ProductNotFoundException();
            }
            var productPrice = product.getPrice();
            var quantity = orderItem.getQuantity();
            orderItem.validatePriceInfo(productPrice);
            orderItem.validateSubtotal(productPrice, quantity);
        });
    }

}
