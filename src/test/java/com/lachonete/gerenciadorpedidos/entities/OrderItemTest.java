package com.lachonete.gerenciadorpedidos.entities;

import static org.junit.jupiter.api.Assertions.*;

import com.lachonete.gerenciadorpedidos.entities.valueobject.*;
import org.junit.jupiter.api.Test;
import com.lachonete.gerenciadorpedidos.entities.exception.InconsistenceOrderItemSubtotalException;
import com.lachonete.gerenciadorpedidos.entities.exception.InconsistenceProductPriceException;

import java.math.BigDecimal;
import java.util.UUID;

class OrderItemTest {

    private final UUID orderUuid = UUID.randomUUID();
    private final Long itemUuid = 20L;
    private final UUID productUuid = UUID.randomUUID();
    private final String productName = "product sample";

    private final OrderId sampleOrderId = new OrderId(orderUuid);
    private final OrderItemId sampleItemId = new OrderItemId(itemUuid);
    private final Money samplePrice = new Money(new BigDecimal("10.50"));
    private final Product sampleProduct = Product.ProductBuilder.aProduct()
            .withId(new ProductId(productUuid))
            .withName(productName)
            .withPrice(samplePrice)
            .build();
    private final int sampleQuantity = 2;
    private final Money expectedSubtotal = new Money(new BigDecimal("21.00"));

    @Test
    void shouldInitializeOrderItemCorrectly() {
        OrderItem item = new OrderItem();
        item.initializeOrderItem(sampleOrderId, sampleItemId);

        assertEquals(sampleOrderId, item.getOrderId());
        assertEquals(sampleItemId, item.getId());
    }

    @Test
    void shouldCalculateSubtotalCorrectly() {
        OrderItem item = OrderItem.OrderItemBuilder.anOrderItem()
                .withProduct(sampleProduct)
                .withQuantity(sampleQuantity)
                .withPrice(samplePrice)
                .build();
        item.setSubTotal(sampleProduct, sampleQuantity);
        assertEquals(expectedSubtotal.getAmount(), item.getSubTotal().getAmount());
    }

    @Test
    void shouldValidatePriceInfoSuccessfully() {
        OrderItem item = OrderItem.OrderItemBuilder.anOrderItem()
                .withPrice(samplePrice)
                .build();

        item.validatePriceInfo(samplePrice); // Should not throw exception
    }

    @Test
    void shouldThrowExceptionWhenPriceInfoIsIncorrect() {
        OrderItem item = OrderItem.OrderItemBuilder.anOrderItem()
                .withPrice(new Money(new BigDecimal("11.00"))) // Different from samplePrice
                .build();

        assertThrows(InconsistenceProductPriceException.class,
                () -> item.validatePriceInfo(samplePrice));
    }

    @Test
    void shouldValidateSubtotalSuccessfully() {
        OrderItem item = OrderItem.OrderItemBuilder.anOrderItem()
                .withSubTotal(expectedSubtotal)
                .withProduct(sampleProduct)
                .withQuantity(sampleQuantity)
                .build();

        item.validateSubtotal(samplePrice, sampleQuantity); // Should not throw exception
    }

    @Test
    void shouldThrowExceptionWhenSubtotalIsIncorrect() {
        OrderItem item = OrderItem.OrderItemBuilder.anOrderItem()
                .withSubTotal(new Money(new BigDecimal("20.00"))) // Different from expected 21.00
                .build();

        assertThrows(InconsistenceOrderItemSubtotalException.class,
                () -> item.validateSubtotal(samplePrice, sampleQuantity));
    }

    @Test
    void shouldBuildOrderItemWithBuilderCorrectly() {
        OrderItem item = OrderItem.OrderItemBuilder.anOrderItem()
                .withId(sampleItemId)
                .withOrderId(sampleOrderId)
                .withProduct(sampleProduct)
                .withQuantity(sampleQuantity)
                .withSubTotal(expectedSubtotal)
                .withPrice(samplePrice)
                .build();

        assertEquals(sampleItemId, item.getId());
        assertEquals(sampleOrderId, item.getOrderId());
        assertEquals(sampleProduct, item.getProduct());
        assertEquals(sampleQuantity, item.getQuantity());
        assertEquals(expectedSubtotal.getAmount(), item.getSubTotal().getAmount());
        assertEquals(samplePrice.getAmount(), item.getPrice().getAmount());
    }

    @Test
    void shouldHandleZeroQuantityInSubtotalCalculation() {
        OrderItem item = OrderItem.OrderItemBuilder.anOrderItem()
                .withProduct(sampleProduct)
                .withQuantity(0)
                .build();

        item.setSubTotal(sampleProduct, 0);
        assertEquals(new BigDecimal("0.00"), item.getSubTotal().getAmount());
    }

    @Test
    void shouldHandleNullProductInSubtotalCalculation() {
        OrderItem item = new OrderItem();
        assertThrows(NullPointerException.class,
                () -> item.setSubTotal(null, sampleQuantity));
    }
}
