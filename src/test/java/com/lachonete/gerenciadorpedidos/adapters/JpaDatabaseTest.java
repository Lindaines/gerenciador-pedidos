package com.lachonete.gerenciadorpedidos.adapters;

import com.lachonete.gerenciadorpedidos.adapters.repositories.CustomerRepository;
import com.lachonete.gerenciadorpedidos.adapters.repositories.OrderRepository;
import com.lachonete.gerenciadorpedidos.adapters.repositories.PaymentRepository;
import com.lachonete.gerenciadorpedidos.adapters.repositories.ProductRepository;
import com.lachonete.gerenciadorpedidos.ports.database.CustomerGateway;
import com.lachonete.gerenciadorpedidos.ports.database.OrderGateway;
import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JpaDatabaseTest {

    // Mock the repository interfaces
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private PaymentRepository paymentRepository; // Though not directly used by gateways in JpaDatabase constructor

    private JpaDatabase jpaDatabase;

    @BeforeEach
    void setUp() {
        // Initialize mocks annotated with @Mock
        MockitoAnnotations.openMocks(this);

        // Instantiate JpaDatabase with the mocked repositories
        jpaDatabase = new JpaDatabase(productRepository, customerRepository, orderRepository, paymentRepository);
    }

    @Test
    void constructor_ShouldInitializeAllGateways() {
        // Given that setup() initializes jpaDatabase

        // When/Then: We just need to assert that the gateways are not null
        // and are of the correct concrete type after construction.
        assertNotNull(jpaDatabase.productGateway(), "ProductGateway should be initialized.");
        assertNotNull(jpaDatabase.customerGateway(), "CustomerGateway should be initialized.");
        assertNotNull(jpaDatabase.orderGateway(), "OrderGateway should be initialized.");

        assertTrue(jpaDatabase.productGateway() instanceof JpaProductGateway, "productGateway should be an instance of JpaProductGateway.");
        assertTrue(jpaDatabase.customerGateway() instanceof JpaCustomerGateway, "customerGateway should be an instance of JpaCustomerGateway.");
        assertTrue(jpaDatabase.orderGateway() instanceof JpaOrderGateway, "orderGateway should be an instance of JpaOrderGateway.");
    }

    @Test
    void productGateway_ShouldReturnTheInitializedProductGateway() {
        // Given that setup() has already initialized the gateways

        // When
        ProductGateway gateway = jpaDatabase.productGateway();

        // Then
        assertNotNull(gateway);
        assertTrue(gateway instanceof JpaProductGateway);
        // Additional assertion: Verify that the same instance is returned on subsequent calls
        assertTrue(gateway == jpaDatabase.productGateway(), "Should return the same instance on repeated calls.");
    }

    @Test
    void customerGateway_ShouldReturnTheInitializedCustomerGateway() {
        // Given that setup() has already initialized the gateways

        // When
        CustomerGateway gateway = jpaDatabase.customerGateway();

        // Then
        assertNotNull(gateway);
        assertTrue(gateway instanceof JpaCustomerGateway);
        assertTrue(gateway == jpaDatabase.customerGateway(), "Should return the same instance on repeated calls.");
    }

    @Test
    void orderGateway_ShouldReturnTheInitializedOrderGateway() {
        // Given that setup() has already initialized the gateways

        // When
        OrderGateway gateway = jpaDatabase.orderGateway();

        // Then
        assertNotNull(gateway);
        assertTrue(gateway instanceof JpaOrderGateway);
        assertTrue(gateway == jpaDatabase.orderGateway(), "Should return the same instance on repeated calls.");
    }
}