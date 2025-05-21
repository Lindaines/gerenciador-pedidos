package com.lachonete.gerenciadorpedidos.api.endpoints.customer.add;

import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerCreatedOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerCreatedViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.add.AddCustomerInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.add.AddCustomerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddCustomerEndpointTest {

    private AddCustomerInputBoundary addCustomerUseCase;
    private CustomerCreatedOutputBoundary customerCreatedPresenter;
    private AddCustomerEndpoint addCustomerEndpoint;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        addCustomerUseCase = mock(AddCustomerInputBoundary.class);
        customerCreatedPresenter = mock(CustomerCreatedOutputBoundary.class);

        // Inject mocks into the endpoint
        addCustomerEndpoint = new AddCustomerEndpoint(addCustomerUseCase, customerCreatedPresenter);

        // Mock HttpServletRequest for ServletUriComponentsBuilder.fromCurrentRequest()
        // This is necessary because the endpoint uses URI.create with MessageFormat,
        // which implicitly relies on Spring's request context for URI building
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/api/v1/customers"));
        when(mockRequest.getRequestURI()).thenReturn("/api/v1/customers");
        when(mockRequest.getContextPath()).thenReturn("");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));
    }



    @Test
    void execute_ShouldHandleUseCaseFailureGracefully() {
        // Given: A valid NewCustomerRequest
        NewCustomerRequest request = NewCustomerRequest.builder()
                .name("Jane Doe")
                .cpf("987.654.321-00")
                .email("jane.doe@example.com")
                .build();

        // And: The use case throws an exception
        doThrow(new RuntimeException("Use case failed")).when(addCustomerUseCase).execute(any(AddCustomerRequest.class));

        // When/Then: Calling execute should throw an exception (if not caught by endpoint)
        assertThrows(RuntimeException.class, () -> addCustomerEndpoint.execute(request),
                "Should rethrow exception from use case if not handled by endpoint.");

        // Verify the use case was still called
        verify(addCustomerUseCase, times(1)).execute(any(AddCustomerRequest.class));
        // Verify presenter was NOT called if use case failed before reaching it
        verify(customerCreatedPresenter, never()).getViewModel();
    }


}