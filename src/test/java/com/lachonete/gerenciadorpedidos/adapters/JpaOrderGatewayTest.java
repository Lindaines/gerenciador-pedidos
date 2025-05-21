package com.lachonete.gerenciadorpedidos.adapters;

import com.lachonete.gerenciadorpedidos.adapters.data.OrderData;
import com.lachonete.gerenciadorpedidos.adapters.data.OrderItemData;
import com.lachonete.gerenciadorpedidos.adapters.repositories.OrderRepository;
import com.lachonete.gerenciadorpedidos.entities.Order;
import com.lachonete.gerenciadorpedidos.entities.OrderItem;
import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.entities.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JpaOrderGatewayTest {

    private OrderRepository orderRepository;
    private JpaOrderGateway jpaOrderGateway;

    // Use reflection to reset the static counter for pickup codes for isolated tests
    private static void resetCounter(int value) throws NoSuchFieldException, IllegalAccessException {
        Field counterField = JpaOrderGateway.class.getDeclaredField("counter");
        counterField.setAccessible(true);
        AtomicInteger counter = (AtomicInteger) counterField.get(null); // static field
        counter.set(value);
    }

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        orderRepository = mock(OrderRepository.class);
        jpaOrderGateway = new JpaOrderGateway(orderRepository);
        // Reset the static counter for each test to ensure predictable pickup codes
        resetCounter(1);
    }

    private OrderData createOrderData(UUID orderId, OrderStatus status, BigDecimal totalPrice, Integer pickupCode, List<OrderItemData> items) {
        OrderData orderData = OrderData.builder()
                .id(orderId)
                .orderStatus(status)
                .price(totalPrice)
                .pickUpCode(pickupCode)
                .items(items)
                .build();
        // Ensure bidirectional relationship is set for testing mapToOrder
        if (items != null) {
            items.forEach(item -> item.setOrder(orderData));
        }
        return orderData;
    }

    private OrderItemData createOrderItemData(Long itemId, UUID productId, int quantity, BigDecimal price, BigDecimal subTotal) {
        return OrderItemData.builder()
                .id(itemId)
                .productId(productId)
                .quantity(quantity)
                .price(price)
                .subTotal(subTotal)
                .build();
    }

    private Order createOrder(UUID orderId, OrderStatus status, BigDecimal totalPrice, Integer pickupCode, List<OrderItem> items) {
        return Order.OrderBuilder.anOrder()
                .withId(new OrderId(orderId))
                .withOrderStatus(status)
                .withPrice(new Money(totalPrice))
                .withPickupCode(pickupCode)
                .withItems(items)
                .build();
    }

    private OrderItem createOrderItem(Long itemId, UUID productId, int quantity, BigDecimal price, BigDecimal subTotal) {
        return OrderItem.OrderItemBuilder.anOrderItem()
                .withId(new OrderItemId(itemId))
                .withProduct(new Product(new ProductId(productId)))
                .withQuantity(quantity)
                .withPrice(new Money(price))
                .withSubTotal(new Money(subTotal))
                .build();
    }

    // --- Tests for getAll ---

    @Test
    void getAll_ShouldReturnListOfOrdersWhenRepositoryHasData() {
        // Given
        UUID orderId1 = UUID.randomUUID();
        UUID orderId2 = UUID.randomUUID();
        var itemId1 = 1L;
        var itemId2 = 2L;
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();

        OrderItemData itemData1 = createOrderItemData(itemId1, productId1, 2, new BigDecimal("10.00"), new BigDecimal("20.00"));
        OrderItemData itemData2 = createOrderItemData(itemId2, productId2, 1, new BigDecimal("30.00"), new BigDecimal("30.00"));

        OrderData orderData1 = createOrderData(orderId1, OrderStatus.RECEBIDO, new BigDecimal("50.00"), 101, Arrays.asList(itemData1));
        OrderData orderData2 = createOrderData(orderId2, OrderStatus.EM_PREPARACAO, new BigDecimal("60.00"), 102, Arrays.asList(itemData2));

        when(orderRepository.findAll()).thenReturn(Arrays.asList(orderData1, orderData2));

        // When
        List<Order> result = jpaOrderGateway.getAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        Order order1 = result.get(0);
        assertEquals(orderId1, order1.getId().getValue());
        assertEquals(OrderStatus.RECEBIDO, order1.getOrderStatus());
        assertEquals(new BigDecimal("50.00"), order1.getPrice().getAmount());
        assertEquals(101, order1.getPickupCode());
        assertEquals(1, order1.getItems().size());
        assertEquals(productId1, order1.getItems().get(0).getProduct().getId().getValue());

        Order order2 = result.get(1);
        assertEquals(orderId2, order2.getId().getValue());
        assertEquals(OrderStatus.EM_PREPARACAO, order2.getOrderStatus());
        assertEquals(new BigDecimal("60.00"), order2.getPrice().getAmount());
        assertEquals(102, order2.getPickupCode());
        assertEquals(1, order2.getItems().size());
        assertEquals(productId2, order2.getItems().get(0).getProduct().getId().getValue());

        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getAll_ShouldReturnEmptyListWhenRepositoryIsEmpty() {
        // Given
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Order> result = jpaOrderGateway.getAll();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(orderRepository, times(1)).findAll();
    }

    // --- Tests for getAllExceptFinished ---

    @Test
    void getAllExceptFinished_ShouldReturnOrdersExcludingFinalized() {
        // Given
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID id3 = UUID.randomUUID();

        OrderData orderData1 = createOrderData(id1, OrderStatus.RECEBIDO, new BigDecimal("10.00"), 1, Collections.emptyList());
        OrderData orderData2 = createOrderData(id2, OrderStatus.FINALIZADO, new BigDecimal("20.00"), 2, Collections.emptyList());
        OrderData orderData3 = createOrderData(id3, OrderStatus.EM_PREPARACAO, new BigDecimal("30.00"), 3, Collections.emptyList());

        when(orderRepository.findAll()).thenReturn(Arrays.asList(orderData1, orderData2, orderData3));

        // When
        List<Order> result = jpaOrderGateway.getAllExceptFinished();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().noneMatch(order -> order.getId().getValue().equals(id2)), "Finalized order should not be in the list.");
        assertTrue(result.stream().anyMatch(order -> order.getId().getValue().equals(id1) && order.getOrderStatus() == OrderStatus.RECEBIDO));
        assertTrue(result.stream().anyMatch(order -> order.getId().getValue().equals(id3) && order.getOrderStatus() == OrderStatus.EM_PREPARACAO));

        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getAllExceptFinished_ShouldReturnEmptyListIfAllAreFinishedOrRepositoryEmpty() {
        // Given
        UUID id1 = UUID.randomUUID();
        OrderData orderData1 = createOrderData(id1, OrderStatus.FINALIZADO, new BigDecimal("10.00"), 1, Collections.emptyList());
        when(orderRepository.findAll()).thenReturn(Arrays.asList(orderData1));

        // When
        List<Order> result = jpaOrderGateway.getAllExceptFinished();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(orderRepository, times(1)).findAll();

        // Test with completely empty repository
        reset(orderRepository); // Reset mock for new scenario
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());
        result = jpaOrderGateway.getAllExceptFinished();
        assertTrue(result.isEmpty());
        verify(orderRepository, times(1)).findAll();
    }

    // --- Tests for add ---

    @Test
    void add_ShouldSaveOrderDataAndReturnMappedOrder() {
        // Given
        UUID productId = UUID.randomUUID();
        OrderItem orderItem = createOrderItem(10L, productId, 1, new BigDecimal("25.00"), new BigDecimal("25.00"));
        Order orderToAdd = createOrder(null, OrderStatus.RECEBIDO, new BigDecimal("25.00"), null, Arrays.asList(orderItem));

        // Mock the save operation to return a saved OrderData with an ID and the generated pickupCode
        ArgumentCaptor<OrderData> orderDataCaptor = ArgumentCaptor.forClass(OrderData.class);
        when(orderRepository.save(orderDataCaptor.capture())).thenAnswer(invocation -> {
            OrderData capturedData = invocation.getArgument(0);
            capturedData.setId(UUID.randomUUID()); // Simulate ID being set by JPA
            return capturedData; // Return the captured instance (which now has an ID)
        });

        // When
        Order result = jpaOrderGateway.add(orderToAdd);

        // Then
        assertNotNull(result);
        assertNotNull(result.getId().getValue()); // ID should be generated by JPA and mapped back
        assertNotNull(result.getPickupCode()); // Pickup code should be generated
        assertEquals(1, result.getPickupCode()); // First call, so it's 1 based on resetCounter(1)
        assertEquals(OrderStatus.RECEBIDO, result.getOrderStatus());
        assertEquals(new BigDecimal("25.00"), result.getPrice().getAmount());
        assertEquals(1, result.getItems().size());
        assertEquals(productId, result.getItems().get(0).getProduct().getId().getValue());

        // Verify that the captured OrderData was correctly built
        OrderData capturedOrderData = orderDataCaptor.getValue();
        assertNotNull(capturedOrderData.getId()); // Should have an ID
        assertEquals(1, capturedOrderData.getPickUpCode());
        assertEquals(OrderStatus.RECEBIDO, capturedOrderData.getOrderStatus());
        assertEquals(new BigDecimal("25.00"), capturedOrderData.getPrice());
        assertEquals(1, capturedOrderData.getItems().size());
        assertEquals(productId, capturedOrderData.getItems().get(0).getProductId());
        assertNotNull(capturedOrderData.getItems().get(0).getOrder()); // Bidirectional relationship should be set

        verify(orderRepository, times(1)).save(any(OrderData.class));
    }

    @Test
    void add_ShouldIncrementPickupCodeForEachCall() {
        // Given
        Order order1 = createOrder(null, OrderStatus.RECEBIDO, new BigDecimal("10.00"), null, Collections.emptyList());
        Order order2 = createOrder(null, OrderStatus.RECEBIDO, new BigDecimal("20.00"), null, Collections.emptyList());

        when(orderRepository.save(any(OrderData.class))).thenAnswer(invocation -> {
            OrderData capturedData = invocation.getArgument(0);
            capturedData.setId(UUID.randomUUID());
            return capturedData;
        });

        // When
        Order result1 = jpaOrderGateway.add(order1);
        Order result2 = jpaOrderGateway.add(order2);

        // Then
        assertEquals(1, result1.getPickupCode());
        assertEquals(2, result2.getPickupCode());
    }

    // --- Tests for getById ---

    @Test
    void getById_ShouldReturnOrderWhenFound() {
        // Given
        UUID orderId = UUID.randomUUID();
        var itemId = 10L;
        UUID productId = UUID.randomUUID();
        OrderItemData itemData = createOrderItemData(itemId, productId, 1, new BigDecimal("15.00"), new BigDecimal("15.00"));
        OrderData foundData = createOrderData(orderId, OrderStatus.PRONTO, new BigDecimal("15.00"), 50, Arrays.asList(itemData));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(foundData));

        // When
        Order result = jpaOrderGateway.getById(orderId);

        // Then
        assertNotNull(result);
        assertEquals(orderId, result.getId().getValue());
        assertEquals(OrderStatus.PRONTO, result.getOrderStatus());
        assertEquals(new BigDecimal("15.00"), result.getPrice().getAmount());
        assertEquals(50, result.getPickupCode());
        assertEquals(1, result.getItems().size());
        assertEquals(productId, result.getItems().get(0).getProduct().getId().getValue());

        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void getById_ShouldReturnNullWhenNotFound() {
        // Given
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // When
        Order result = jpaOrderGateway.getById(orderId);

        // Then
        assertNull(result);
        verify(orderRepository, times(1)).findById(orderId);
    }

    // --- Tests for updateStatus ---

    @Test
    void updateStatus_ShouldUpdateOrderStatusAndSaveOrder() {
        // Given
        UUID orderIdToUpdate = UUID.randomUUID();
        var itemId = 10L;
        UUID productId = UUID.randomUUID();
        OrderItemData itemData = createOrderItemData(itemId, productId, 1, new BigDecimal("10.00"), new BigDecimal("10.00"));
        // Start with RECEBIDO status
        OrderData existingOrderData = createOrderData(orderIdToUpdate, OrderStatus.RECEBIDO, new BigDecimal("10.00"), 100, Arrays.asList(itemData));
        Order existingOrderDomain = createOrder(orderIdToUpdate, OrderStatus.RECEBIDO, new BigDecimal("10.00"), 100, Arrays.asList(createOrderItem(itemId, productId, 1, new BigDecimal("10.00"), new BigDecimal("10.00"))));


        // Mock findById to return the existing order
        when(orderRepository.findById(orderIdToUpdate)).thenReturn(Optional.of(existingOrderData));

        // Mock save to capture the updated OrderData
        ArgumentCaptor<OrderData> orderDataCaptor = ArgumentCaptor.forClass(OrderData.class);
        when(orderRepository.save(orderDataCaptor.capture())).thenReturn(null); // Return null, as void method

        // When
        OrderStatus newStatus = OrderStatus.PRONTO;
        jpaOrderGateway.updateStatus(orderIdToUpdate, newStatus);

        // Then
        // Verify findById was called
        verify(orderRepository, times(1)).findById(orderIdToUpdate);
        // Verify save was called
        verify(orderRepository, times(1)).save(any(OrderData.class));

        // Verify the captured OrderData has the updated status and correct ID
        OrderData capturedOrderData = orderDataCaptor.getValue();
        assertNotNull(capturedOrderData);
        assertEquals(orderIdToUpdate, capturedOrderData.getId(), "ID should be preserved for update.");
        assertEquals(newStatus, capturedOrderData.getOrderStatus(), "Order status should be updated.");
        assertEquals(existingOrderData.getPrice(), capturedOrderData.getPrice(), "Price should remain the same.");
        assertEquals(existingOrderData.getPickUpCode(), capturedOrderData.getPickUpCode(), "Pickup code should remain the same.");
        // Ensure items are also correctly mapped and linked back
        assertEquals(existingOrderData.getItems().size(), capturedOrderData.getItems().size());
        assertEquals(existingOrderData.getItems().get(0).getProductId(), capturedOrderData.getItems().get(0).getProductId());
        assertNotNull(capturedOrderData.getItems().get(0).getOrder()); // Bidirectional relationship should be set

    }

    @Test
    void updateStatus_ShouldThrowExceptionIfOrderNotFound() {
        // Given
        UUID nonExistentOrderId = UUID.randomUUID();
        when(orderRepository.findById(nonExistentOrderId)).thenReturn(Optional.empty());

        // When / Then
        // The getById() call inside updateStatus() will return null,
        // and then mapToOrder(null) will throw NullPointerException, or order.updateStatus() will throw it.
        assertThrows(NullPointerException.class, () -> jpaOrderGateway.updateStatus(nonExistentOrderId, OrderStatus.PRONTO),
                "Should throw NullPointerException if order not found for update.");

        verify(orderRepository, times(1)).findById(nonExistentOrderId);
        verify(orderRepository, never()).save(any(OrderData.class)); // Save should not be called
    }

    // --- Tests for toOrderItemData mapping helper ---
    @Test
    void toOrderItemData_ShouldMapOrderItemToOrderItemDataCorrectly() {
        // Given
        var itemId = 10L;
        UUID productId = UUID.randomUUID();
        OrderItem orderItem = createOrderItem(itemId, productId, 3, new BigDecimal("5.00"), new BigDecimal("15.00"));

        // When
        OrderItemData result = jpaOrderGateway.toOrderItemData(orderItem);

        // Then
        assertNotNull(result);
        assertNull(result.getId(), "ID is not mapped by toOrderItemData directly from OrderItem's ID, if it's the DB ID.");
        assertEquals(productId, result.getProductId());
        assertEquals(3, result.getQuantity());
        assertEquals(new BigDecimal("5.00"), result.getPrice());
        assertEquals(new BigDecimal("15.00"), result.getSubTotal());
    }
}