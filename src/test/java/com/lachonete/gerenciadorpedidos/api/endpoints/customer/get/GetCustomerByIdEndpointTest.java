package com.lachonete.gerenciadorpedidos.api.endpoints.customer.get;

import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.GetCustomerByIdRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.GetCustomerInputBoundary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetCustomerByIdEndpointTest {

    private GetCustomerInputBoundary getCustomerUseCase;
    private CustomerOutputBoundary customerPresenter;
    private GetCustomerByIdEndpoint getCustomerByIdEndpoint;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        getCustomerUseCase = mock(GetCustomerInputBoundary.class);
        customerPresenter = mock(CustomerOutputBoundary.class);

        // Inject mocks into the endpoint
        getCustomerByIdEndpoint = new GetCustomerByIdEndpoint(getCustomerUseCase, customerPresenter);
    }

    @Test
    void execute_ShouldCallUseCaseAndReturnOkResponseWithCustomer() {
        // Given: A customer ID and an expected CustomerViewModel
        UUID customerId = UUID.randomUUID();
        CustomerViewModel expectedViewModel = CustomerViewModel.builder()
                .id(customerId)
                .name("Alice Wonderland")
                .cpf("999.888.777-66")
                .email("alice@example.com")
                .build();

        // And: The presenter is configured to return the expected ViewModel
        when(customerPresenter.getViewModel()).thenReturn(expectedViewModel);

        // When: The execute method is called with the customer ID
        ResponseEntity responseEntity = getCustomerByIdEndpoint.execute(customerId);

        // Then:
        // 1. The use case's execute method should be called exactly once
        verify(getCustomerUseCase, times(1)).execute(any(GetCustomerByIdRequest.class));

        // 2. Capture the GetCustomerByIdRequest argument passed to the use case
        ArgumentCaptor<GetCustomerByIdRequest> requestCaptor = ArgumentCaptor.forClass(GetCustomerByIdRequest.class);
        verify(getCustomerUseCase).execute(requestCaptor.capture());
        GetCustomerByIdRequest capturedRequest = requestCaptor.getValue();

        // 3. Verify the captured request's ID
        assertNotNull(capturedRequest, "Captured GetCustomerByIdRequest should not be null.");
        assertEquals(customerId, capturedRequest.getId(), "Customer ID in request should match.");

        // 4. The presenter's getViewModel method should be called
        verify(customerPresenter, times(1)).getViewModel();

        // 5. The response status should be 200 OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Response status should be OK.");

        // 6. The response body should be the ViewModel from the presenter
        assertEquals(expectedViewModel, responseEntity.getBody(), "Response body should be the ViewModel.");
    }

    @Test
    void execute_ShouldHandleUseCaseNotFindingCustomer() {
        // Given: A customer ID
        UUID customerId = UUID.randomUUID();

        // And: The presenter is configured to return null (simulating customer not found)
        when(customerPresenter.getViewModel()).thenReturn(null);

        // When: The execute method is called
        ResponseEntity responseEntity = getCustomerByIdEndpoint.execute(customerId);

        // Then:
        // 1. The use case should still be called
        verify(getCustomerUseCase, times(1)).execute(any(GetCustomerByIdRequest.class));

        // 2. The presenter's getViewModel should be called
        verify(customerPresenter, times(1)).getViewModel();

        // 3. The response status should be 200 OK (as per endpoint logic)
        // Note: In a real application, a "not found" scenario often returns 404 NOT_FOUND.
        // This test reflects the current endpoint's logic which always returns 200 OK with whatever the presenter provides.
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Response status should be OK.");

        // 4. The response body should be null
        assertNull(responseEntity.getBody(), "Response body should be null if ViewModel is null (customer not found).");
    }

    @Test
    void execute_ShouldHandleUseCaseThrowingException() {
        // Given: A customer ID
        UUID customerId = UUID.randomUUID();

        // And: The use case throws an exception (e.g., database error)
        doThrow(new RuntimeException("Database error")).when(getCustomerUseCase).execute(any(GetCustomerByIdRequest.class));

        // When/Then: Calling execute should throw the exception if not handled by endpoint
        assertThrows(RuntimeException.class, () -> getCustomerByIdEndpoint.execute(customerId),
                "Should rethrow exception from use case if not handled by endpoint.");

        // Verify the use case was called
        verify(getCustomerUseCase, times(1)).execute(any(GetCustomerByIdRequest.class));
        // Verify presenter was NOT called if use case failed before reaching it
        verify(customerPresenter, never()).getViewModel();
    }
}