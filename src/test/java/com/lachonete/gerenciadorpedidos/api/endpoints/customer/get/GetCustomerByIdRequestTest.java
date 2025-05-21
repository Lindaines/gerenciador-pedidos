package com.lachonete.gerenciadorpedidos.api.endpoints.customer.get;

import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.GetCustomerByIdRequest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GetCustomerByIdRequestTest {

    @Test
    void constructor_ShouldSetIdCorrectly() {
        // Given: A UUID
        UUID expectedId = UUID.randomUUID();

        // When: Instantiating GetCustomerByIdRequest using the constructor
        GetCustomerByIdRequest request = new GetCustomerByIdRequest(expectedId);

        // Then: The ID should be set correctly
        assertNotNull(request, "Request object should not be null.");
        assertEquals(expectedId, request.getId(), "The ID set by the constructor should match the expected ID.");
    }

    @Test
    void builder_ShouldSetIdCorrectly() {
        // Given: A UUID
        UUID expectedId = UUID.randomUUID();

        // When: Instantiating GetCustomerByIdRequest using the builder
        GetCustomerByIdRequest request = GetCustomerByIdRequest.builder()
                .id(expectedId)
                .build();

        // Then: The ID should be set correctly
        assertNotNull(request, "Request object should not be null when built.");
        assertEquals(expectedId, request.getId(), "The ID set by the builder should match the expected ID.");
    }

    @Test
    void getId_ShouldReturnCorrectId() {
        // Given: A GetCustomerByIdRequest instance with a specific ID
        UUID expectedId = UUID.randomUUID();
        GetCustomerByIdRequest request = new GetCustomerByIdRequest(expectedId);

        // When: Calling getId()
        UUID actualId = request.getId();

        // Then: The returned ID should match the expected ID
        assertEquals(expectedId, actualId, "getId() should return the correct ID.");
    }

    @Test
    void builder_ShouldHandleNullId() {
        // Given: A null UUID
        UUID expectedId = null;

        // When: Building a request with a null ID
        GetCustomerByIdRequest request = GetCustomerByIdRequest.builder()
                .id(expectedId)
                .build();

        // Then: The ID should be null
        assertNotNull(request, "Request object should not be null when built with null ID.");
        assertEquals(expectedId, request.getId(), "The ID should be null when set to null by the builder.");
    }
}