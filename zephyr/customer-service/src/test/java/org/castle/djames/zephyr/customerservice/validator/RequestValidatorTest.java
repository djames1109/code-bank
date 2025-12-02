package org.castle.djames.zephyr.customerservice.validator;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.castle.djames.zephyr.customerservice.dto.CustomerRequest;
import org.castle.djames.zephyr.customerservice.validator.groups.AddCustomerGroup;
import org.castle.djames.zephyr.customerservice.validator.groups.UpdateCustomerGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

//    ========== REGISTER CUSTOMER

    @Test
    void testValidateRegisterCustomerRequest_validInput_noViolations() {
        CustomerRequest request = new CustomerRequest(
                "John",
                "Doe",
                "123456789",
                LocalDate.of(1990, 1, 1),
                "john.doe@example.com",
                "+1234567890",
                "123 Main St"
        );

        var violations = validator.validate(request, AddCustomerGroup.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidateRegisterCustomerRequest_emptyFirstName_hasViolation() {
        CustomerRequest request = new CustomerRequest(
                "",
                "Doe",
                "123456789",
                LocalDate.of(1990, 1, 1),
                "john.doe@example.com",
                "+1234567890",
                "123 Main St"
        );

        var violations = validator.validate(request, AddCustomerGroup.class);
        assertEquals(1, violations.size());
        assertEquals("firstName", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testValidateRegisterCustomerRequest_emptyLastName_hasViolation() {
        CustomerRequest request = new CustomerRequest(
                "John",
                "",
                "123456789",
                LocalDate.of(1990, 1, 1),
                "john.doe@example.com",
                "+1234567890",
                "123 Main St"
        );

        var violations = validator.validate(request, AddCustomerGroup.class);
        assertEquals(1, violations.size());
        assertEquals("lastName", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testValidateRegisterCustomerRequest_emptyNationalId_hasViolation() {
        CustomerRequest request = new CustomerRequest(
                "John",
                "Doe",
                "",
                LocalDate.of(1990, 1, 1),
                "john.doe@example.com",
                "+1234567890",
                "123 Main St"
        );

        var violations = validator.validate(request, AddCustomerGroup.class);
        assertEquals(1, violations.size());
        assertEquals("nationalId", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testValidateRegisterCustomerRequest_nullBirthDate_hasViolation() {
        CustomerRequest request = new CustomerRequest(
                "John",
                "Doe",
                "123456789",
                null,
                "john.doe@example.com",
                "+1234567890",
                "123 Main St"
        );

        var violations = validator.validate(request, AddCustomerGroup.class);
        assertEquals(1, violations.size());
        assertEquals("birthDate", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testValidateRegisterCustomerRequest_invalidEmail_hasViolation() {
        CustomerRequest request = new CustomerRequest(
                "John",
                "Doe",
                "123456789",
                LocalDate.of(1990, 1, 1),
                "invalid-email",
                "+1234567890",
                "123 Main St"
        );

        var violations = validator.validate(request, AddCustomerGroup.class);
        assertEquals(1, violations.size());
        assertEquals("email", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testValidateRegisterCustomerRequest_emptyPhone_hasViolation() {
        CustomerRequest request = new CustomerRequest(
                "John",
                "Doe",
                "123456789",
                LocalDate.of(1990, 1, 1),
                "john.doe@example.com",
                "",
                "123 Main St"
        );

        var violations = validator.validate(request, AddCustomerGroup.class);
        assertEquals(1, violations.size());
        assertEquals("phone", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testValidateRegisterCustomerRequest_emptyAddress_hasViolation() {
        CustomerRequest request = new CustomerRequest(
                "John",
                "Doe",
                "123456789",
                LocalDate.of(1990, 1, 1),
                "john.doe@example.com",
                "+1234567890",
                ""
        );

        var violations = validator.validate(request, AddCustomerGroup.class);
        assertEquals(1, violations.size());
        assertEquals("address", violations.iterator().next().getPropertyPath().toString());
    }

//    ========== UPDATE CUSTOMER

    @Test
    void testValidateUpdateCustomerRequest_validInput_noViolations() {
        CustomerRequest request = new CustomerRequest(
                null,
                null,
                null,
                null,
                "john.doe@example.com",
                null,
                null
        );

        var violations = validator.validate(request, UpdateCustomerGroup.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidateUpdateCustomerRequest_invalidEmail_hasViolation() {
        CustomerRequest request = new CustomerRequest(
                null,
                null,
                null,
                null,
                "invalid-email",
                null,
                null
        );

        var violations = validator.validate(request, UpdateCustomerGroup.class);
        assertEquals(1, violations.size());
        assertEquals("email", violations.iterator().next().getPropertyPath().toString());
    }

}