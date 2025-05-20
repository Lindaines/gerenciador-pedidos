package com.lachonete.gerenciadorpedidos.usecases.order;

import com.lachonete.gerenciadorpedidos.entities.Order;
import com.lachonete.gerenciadorpedidos.entities.OrderItem;
import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.entities.valueobject.*;
import com.lachonete.gerenciadorpedidos.ports.database.OrderGateway;
import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrderCreatedOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.add.CheckoutOrderRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.add.CheckoutOrderResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.add.OrderItemRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.add.AddPaymentInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.add.AddPaymentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CheckoutOrderTest {

    private OrderCreatedOutputBoundary presenter;
    private OrderGateway orderGateway;
    private ProductGateway productGateway;
    private AddPaymentInputBoundary addPaymentUseCase;
    private CheckoutOrder checkoutOrder;

    @BeforeEach
    void setUp() {
        presenter = Mockito.mock(OrderCreatedOutputBoundary.class);
        orderGateway = Mockito.mock(OrderGateway.class);
        productGateway = Mockito.mock(ProductGateway.class);
        addPaymentUseCase = Mockito.mock(AddPaymentInputBoundary.class);
        checkoutOrder = new CheckoutOrder(presenter, orderGateway, productGateway, addPaymentUseCase);
    }

    private CheckoutOrderRequest createTestRequest(UUID productId) {
        OrderItemRequest item = new OrderItemRequest(
                productId,
                2,
                new BigDecimal("10.00"),
                new BigDecimal("20.00")
        );
        return new CheckoutOrderRequest(List.of(item));
    }

    @Test
    void execute_shouldCreateOrderAndPresentResponse() {
        // Arrange
        UUID productId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        String paymentId = "PAY-" + UUID.randomUUID();

        CheckoutOrderRequest request = createTestRequest(productId);

        Product mockProduct = new Product(new ProductId(productId));
        mockProduct.setPrice(new Money(new BigDecimal("10.00")));

        Order savedOrder = Order.OrderBuilder.anOrder()
                .withId(new OrderId(orderId))
                .withPickupCode(12345)
                .withOrderStatus(OrderStatus.CRIADO)
                .build();

        when(productGateway.getById(productId)).thenReturn(mockProduct);
        when(orderGateway.add(any(Order.class))).thenReturn(savedOrder);
        when(addPaymentUseCase.execute(any(AddPaymentRequest.class))).thenReturn(paymentId);

        // Act
        checkoutOrder.execute(request);

        // Assert
        verify(orderGateway, times(2)).add(any(Order.class));

        ArgumentCaptor<AddPaymentRequest> paymentCaptor = ArgumentCaptor.forClass(AddPaymentRequest.class);
        verify(addPaymentUseCase).execute(paymentCaptor.capture());
        assertEquals(orderId, paymentCaptor.getValue().getOrder().getId().getValue());

        ArgumentCaptor<CheckoutOrderResponse> responseCaptor = ArgumentCaptor.forClass(CheckoutOrderResponse.class);
        verify(presenter).present(responseCaptor.capture());

        CheckoutOrderResponse response = responseCaptor.getValue();
        assertEquals(orderId, response.getId());
        assertEquals(12345, response.getPickupCode());
        assertEquals(paymentId, response.getPaymentId());
    }

    @Test
    void mapToOrder_shouldCorrectlyMapRequestToOrder() {
        // Arrange
        UUID productId = UUID.randomUUID();
        CheckoutOrderRequest request = createTestRequest(productId);

        // Act
        Order order = checkoutOrder.mapToOrder(request);

        // Assert
        assertEquals(1, order.getItems().size());
        OrderItem item = order.getItems().get(0);
        assertEquals(productId, item.getProduct().getId().getValue());
        assertEquals(new BigDecimal("10.00"), item.getPrice().getAmount());
        assertEquals(2, item.getQuantity());
        assertEquals(new BigDecimal("20.00"), item.getSubTotal().getAmount());
    }

    @Test
    void validateOrderItemInfo_shouldThrowWhenProductNotFound() {
        // Arrange
        UUID productId = UUID.randomUUID();
        Order order = Order.OrderBuilder.anOrder()
                .withItems(List.of(
                        OrderItem.OrderItemBuilder.anOrderItem()
                                .withProduct(new Product(new ProductId(productId)))
                                .withQuantity(2)
                                .withPrice(new Money(new BigDecimal("10.00"))) // Unit price
                                .withSubTotal(new Money(new BigDecimal("20.00"))) // Quantity × Unit price
                                .build()
                ))
                .build();


        when(productGateway.getById(productId)).thenReturn(null);

        // Act & Assert
        assertThrows(ProductGateway.ProductNotFoundException.class,
                () -> checkoutOrder.validateOrderItemInfo(order));
    }

    @Test
    void validateOrderItemInfo_shouldValidatePriceAndSubtotal() {
        // Arrange
        UUID productId = UUID.randomUUID();
        Order order = Order.OrderBuilder.anOrder()
                .withItems(List.of(
                        OrderItem.OrderItemBuilder.anOrderItem()
                                .withProduct(new Product(new ProductId(productId)))
                                .withQuantity(2)
                                .withPrice(new Money(new BigDecimal("10.00"))) // Unit price
                                .withSubTotal(new Money(new BigDecimal("20.00"))) // Quantity × Unit price
                                .build()
                ))
                .build();

        Product mockProduct = new Product(new ProductId(productId));
        mockProduct.setPrice(new Money(new BigDecimal("10.00")));

        when(productGateway.getById(productId)).thenReturn(mockProduct);

        // Act
        checkoutOrder.validateOrderItemInfo(order);

        // No exception means validation passed
    }



    @Test
    void addOrder_shouldDelegateToGateway() {
        // Arrange
        Order order = Order.OrderBuilder.anOrder().build();
        Order expectedSavedOrder = Order.OrderBuilder.anOrder()
                .withId(new OrderId(UUID.randomUUID()))
                .build();

        when(orderGateway.add(order)).thenReturn(expectedSavedOrder);

        // Act
        Order result = checkoutOrder.addOrder(order);

        // Assert
        assertSame(expectedSavedOrder, result);
        verify(orderGateway).add(order);
    }



    @Test
    void execute_shouldCalculateTotalPriceCorrectly() {
        // Arrange
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();
        String paymentId = "PAY-" + UUID.randomUUID();

        OrderItemRequest item1 = new OrderItemRequest(
                productId1,
                2,
                new BigDecimal("10.00"),
                new BigDecimal("20.00")
        );
        OrderItemRequest item2 = new OrderItemRequest(
                productId2,
                4, new BigDecimal("5.00"),

                new BigDecimal("20.00")
        );
        CheckoutOrderRequest request = new CheckoutOrderRequest(List.of(item1, item2));

        Product mockProduct1 = new Product(new ProductId(productId1));
        mockProduct1.setPrice(new Money(new BigDecimal("10.00")));

        Product mockProduct2 = new Product(new ProductId(productId2));
        mockProduct2.setPrice(new Money(new BigDecimal("5.00")));

        Order savedOrder = Order.OrderBuilder.anOrder()
                .withId(new OrderId(UUID.randomUUID()))
                .withPickupCode(12345)
                .build();

        when(productGateway.getById(productId1)).thenReturn(mockProduct1);
        when(productGateway.getById(productId2)).thenReturn(mockProduct2);
        when(orderGateway.add(any(Order.class))).thenReturn(savedOrder);
        when(addPaymentUseCase.execute(any(AddPaymentRequest.class))).thenReturn(paymentId);

        // Act
        checkoutOrder.execute(request);

        // Assert
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderGateway, times(2)).add(orderCaptor.capture());

        Order capturedOrder = orderCaptor.getAllValues().get(0);
        assertEquals(new BigDecimal("40.00"), capturedOrder.getPrice().getAmount());
    }


}