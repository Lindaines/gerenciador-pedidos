package com.lachonete.gerenciadorpedidos.api.endpoints.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.lachonete.gerenciadorpedidos.entities.exception.InconsistenceOrderItemSubtotalException;
import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationExceptionHandlerTest {

    private ApplicationExceptionHandler handler;
    private HttpServletRequest mockHttpServletRequest;
    private WebRequest mockWebRequest;

    @BeforeEach
    void setUp() {
        handler = new ApplicationExceptionHandler();
        mockHttpServletRequest = mock(HttpServletRequest.class);
        // Using ServletWebRequest for handleHttpMessageNotReadable as it's a concrete WebRequest
        mockWebRequest = new ServletWebRequest(mockHttpServletRequest);

        // Reset ErrorMessageMap for each test to ensure consistency if it were modified
        // (though in this setup it's static final, good practice for mutable static maps)
        ErrorMessageMap.errors.clear();
        ErrorMessageMap.errors.put(ProductGateway.BadRequest.class, "Invalid request data provided.");
        ErrorMessageMap.errors.put(ProductGateway.NotFound.class, "Requested resource not found.");
        ErrorMessageMap.errors.put(InconsistenceOrderItemSubtotalException.class, "Price mismatch detected for order item.");
    }


    @Test
    void handleControllerException_ShouldReturnBadRequestForInconsistenceOrderItemSubtotalException() {
        // Given
        InconsistenceOrderItemSubtotalException exception = new InconsistenceOrderItemSubtotalException("Price mismatch");

        // When
        ResponseEntity<ErrorResponse> response = handler.handleControllerException(mockHttpServletRequest, exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getErrors().size());
        assertEquals("Price mismatch detected for order item.", response.getBody().getErrors().get(0));
    }

    @Test
    void handleControllerException_ShouldReturnInternalServerErrorForGenericException() {
        // Given
        RuntimeException exception = new RuntimeException("Something unexpected happened");

        // When
        ResponseEntity<ErrorResponse> response = handler.handleControllerException(mockHttpServletRequest, exception);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getErrors().size());
        assertEquals("Oops! Something really bad happened and we couldn't recover.", response.getBody().getErrors().get(0));
    }



    @Test
    void handleHttpMessageNotReadable_ShouldDelegateToSuperclassForOtherCauses() {
        // Given: An HttpMessageNotReadableException with a cause that is not InvalidFormatException or JsonParseException
        RuntimeException otherCause = new RuntimeException("Some other parsing error");
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(
                "Unknown parsing error"
        );

        // Spy on the handler to verify superclass method call
        ApplicationExceptionHandler spyHandler = spy(handler);

        // When
        // Call the method. We cannot directly mock super.handleHttpMessageNotReadable without
        // using PowerMock or directly testing the spy.
        // Instead, we confirm if our custom handling paths are NOT taken.
        ResponseEntity<Object> response = spyHandler.handleHttpMessageNotReadable(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, mockWebRequest);

        // Then: The response will be from Spring's default handler, which often returns
        // 400 Bad Request with a generic message if no other specific handler is present.
        // We can't easily assert the *exact* superclass behavior without more setup,
        // but we can assert that our custom parsing methods were NOT invoked.
        // The default Spring behavior for this specific exception is usually 400.
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // The body might vary depending on Spring Boot version/configuration.
        // A simple check is that it's not our custom ErrorResponse unless Spring
        // configured it to be, but for a pure unit test, it's enough to ensure
        // our custom handling didn't interfere.
        assertFalse(response.getBody() instanceof ErrorResponse);
        // We verify that the internal private methods for specific parsing were not called
        verify(spyHandler, never()).parseInvalidFormatException(any(InvalidFormatException.class));
        verify(spyHandler, never()).parseJsonParseException(any(JsonParseException.class));
    }



    @Test
    void getErrorMessageForInvalidFormatException_ShouldHandleExceptionDuringFormatting() {
        // Given
        InvalidFormatException ife = mock(InvalidFormatException.class);
        // Simulate a scenario where getPath() throws an exception
        when(ife.getPath()).thenThrow(new RuntimeException("Path error"));

        // When
        String errorMessage = handler.getErrorMessageForInvalidFormatException(ife);

        // Then: Should fall back to the default message pattern
        assertEquals("Invalid format for property `{0}'", errorMessage);
    }
}