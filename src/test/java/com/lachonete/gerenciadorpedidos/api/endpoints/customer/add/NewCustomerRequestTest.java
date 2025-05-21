package com.lachonete.gerenciadorpedidos.api.endpoints.customer.add;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NewCustomerRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        // Initialize the validator before each test
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validNewCustomerRequest_ShouldHaveNoViolations() {
        // Given: A NewCustomerRequest with all valid fields
        NewCustomerRequest request = NewCustomerRequest.builder()
                .name("Alice Smith")
                .cpf("123.456.789-01")
                .email("alice.smith@example.com")
                .build();

        // When: Validating the request
        Set<ConstraintViolation<NewCustomerRequest>> violations = validator.validate(request);

        // Then: There should be no violations
        assertTrue(violations.isEmpty(), "Valid request should have no violations.");
    }

    @Test
    void blankName_ShouldHaveViolation() {
        // Given: A NewCustomerRequest with a blank name
        NewCustomerRequest request = NewCustomerRequest.builder()
                .name("") // Blank name
                .cpf("123.456.789-01")
                .email("test@example.com")
                .build();

        // When: Validating the request
        Set<ConstraintViolation<NewCustomerRequest>> violations = validator.validate(request);

        // Then: There should be one violation for the name field
        assertEquals(1, violations.size(), "Should have 1 violation for blank name.");
        ConstraintViolation<NewCustomerRequest> violation = violations.iterator().next();
        assertEquals("name", violation.getPropertyPath().toString(), "Violation should be on 'name' field.");
        assertEquals("NotBlank", violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
    }

    @Test
    void nullName_ShouldHaveViolation() {
        // Given: A NewCustomerRequest with a null name
        NewCustomerRequest request = NewCustomerRequest.builder()
                .name(null) // Null name
                .cpf("123.456.789-01")
                .email("test@example.com")
                .build();

        // When: Validating the request
        Set<ConstraintViolation<NewCustomerRequest>> violations = validator.validate(request);

        // Then: There should be one violation for the name field
        assertEquals(1, violations.size(), "Should have 1 violation for null name.");
        ConstraintViolation<NewCustomerRequest> violation = violations.iterator().next();
        assertEquals("name", violation.getPropertyPath().toString(), "Violation should be on 'name' field.");
        assertEquals("NotBlank", violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
    }

    @Test
    void blankCpf_ShouldHaveViolation() {
        // Given: A NewCustomerRequest with a blank CPF
        NewCustomerRequest request = NewCustomerRequest.builder()
                .name("Test Name")
                .cpf("") // Blank CPF
                .email("test@example.com")
                .build();

        // When: Validating the request
        Set<ConstraintViolation<NewCustomerRequest>> violations = validator.validate(request);

        // Then: There should be one violation for the CPF field
        assertEquals(1, violations.size(), "Should have 1 violation for blank CPF.");
        ConstraintViolation<NewCustomerRequest> violation = violations.iterator().next();
        assertEquals("cpf", violation.getPropertyPath().toString(), "Violation should be on 'cpf' field.");
        assertEquals("NotBlank", violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
    }

    @Test
    void nullCpf_ShouldHaveViolation() {
        // Given: A NewCustomerRequest with a null CPF
        NewCustomerRequest request = NewCustomerRequest.builder()
                .name("Test Name")
                .cpf(null) // Null CPF
                .email("test@example.com")
                .build();

        // When: Validating the request
        Set<ConstraintViolation<NewCustomerRequest>> violations = validator.validate(request);

        // Then: There should be one violation for the CPF field
        assertEquals(1, violations.size(), "Should have 1 violation for null CPF.");
        ConstraintViolation<NewCustomerRequest> violation = violations.iterator().next();
        assertEquals("cpf", violation.getPropertyPath().toString(), "Violation should be on 'cpf' field.");
        assertEquals("NotBlank", violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
    }

    @Test
    void blankEmail_ShouldHaveViolation() {
        // Given: A NewCustomerRequest with a blank email
        NewCustomerRequest request = NewCustomerRequest.builder()
                .name("Test Name")
                .cpf("123.456.789-01")
                .email("") // Blank email
                .build();

        // When: Validating the request
        Set<ConstraintViolation<NewCustomerRequest>> violations = validator.validate(request);

        // Then: There should be one violation for the email field
        assertEquals(1, violations.size(), "Should have 1 violation for blank email.");
        ConstraintViolation<NewCustomerRequest> violation = violations.iterator().next();
        assertEquals("email", violation.getPropertyPath().toString(), "Violation should be on 'email' field.");
        assertEquals("NotBlank", violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
    }

    @Test
    void nullEmail_ShouldHaveViolation() {
        // Given: A NewCustomerRequest with a null email
        NewCustomerRequest request = NewCustomerRequest.builder()
                .name("Test Name")
                .cpf("123.456.789-01")
                .email(null) // Null email
                .build();

        // When: Validating the request
        Set<ConstraintViolation<NewCustomerRequest>> violations = validator.validate(request);

        // Then: There should be one violation for the email field
        assertEquals(1, violations.size(), "Should have 1 violation for null email.");
        ConstraintViolation<NewCustomerRequest> violation = violations.iterator().next();
        assertEquals("email", violation.getPropertyPath().toString(), "Violation should be on 'email' field.");
        assertEquals("NotBlank", violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
    }

}