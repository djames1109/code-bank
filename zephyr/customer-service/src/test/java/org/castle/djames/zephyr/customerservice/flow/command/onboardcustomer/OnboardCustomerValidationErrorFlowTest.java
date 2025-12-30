package org.castle.djames.zephyr.customerservice.flow.command.onboardcustomer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.castle.djames.zephyr.customerservice.dto.CustomerRequest;
import org.castle.djames.zephyr.customerservice.flow.command.BaseCommandTest;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class OnboardCustomerValidationErrorFlowTest extends BaseCommandTest {

    // ========== EMPTY STRING VIOLATIONS ==========
    @Test
    void testOnboardCustomer_emptyFirstName_returnsBadRequest() {
        testValidationError(new CustomerRequest("", "Doe", "123456789",
                LocalDate.of(1990, 1, 1), "john.doe@email.com", "+1234567890", "123 Main St"),
            "firstName must not be blank");
    }

    @Test
    void testOnboardCustomer_emptyLastName_returnsBadRequest() {
        testValidationError(new CustomerRequest("John", "", "123456789",
                LocalDate.of(1990, 1, 1), "john.doe@email.com", "+1234567890", "123 Main St"),
            "lastName must not be blank");
    }

    @Test
    void testOnboardCustomer_emptyNationalId_returnsBadRequest() {
        testValidationError(new CustomerRequest("John", "Doe", "",
                LocalDate.of(1990, 1, 1), "john.doe@email.com", "+1234567890", "123 Main St"),
            "nationalId must not be blank");
    }

    @Test
    void testOnboardCustomer_emptyPhone_returnsBadRequest() {
        testValidationError(new CustomerRequest("John", "Doe", "123456789",
                LocalDate.of(1990, 1, 1), "john.doe@email.com", "", "123 Main St"),
            "phone must not be blank");
    }

    @Test
    void testOnboardCustomer_emptyAddress_returnsBadRequest() {
        testValidationError(new CustomerRequest("John", "Doe", "123456789",
                LocalDate.of(1990, 1, 1), "john.doe@email.com", "+1234567890", ""),
            "address must not be blank");
    }

    // ========== WHITESPACE-ONLY VIOLATIONS ==========
    @Test
    void testOnboardCustomer_whitespaceFirstName_returnsBadRequest() {
        testValidationError(new CustomerRequest("   ", "Doe", "123456789",
                LocalDate.of(1990, 1, 1), "john.doe@email.com", "+1234567890", "123 Main St"),
            "firstName must not be blank");
    }

    @Test
    void testOnboardCustomer_whitespaceLastName_returnsBadRequest() {
        testValidationError(new CustomerRequest("John", "   ", "123456789",
                LocalDate.of(1990, 1, 1), "john.doe@email.com", "+1234567890", "123 Main St"),
            "lastName must not be blank");
    }

    @Test
    void testOnboardCustomer_whitespaceNationalId_returnsBadRequest() {
        testValidationError(new CustomerRequest("John", "Doe", "   ",
                LocalDate.of(1990, 1, 1), "john.doe@email.com", "+1234567890", "123 Main St"),
            "nationalId must not be blank");
    }

    @Test
    void testOnboardCustomer_whitespacePhone_returnsBadRequest() {
        testValidationError(new CustomerRequest("John", "Doe", "123456789",
                LocalDate.of(1990, 1, 1), "john.doe@email.com", "   ", "123 Main St"),
            "phone must not be blank");
    }

    @Test
    void testOnboardCustomer_whitespaceAddress_returnsBadRequest() {
        testValidationError(new CustomerRequest("John", "Doe", "123456789",
                LocalDate.of(1990, 1, 1), "john.doe@email.com", "+1234567890", "   "),
            "address must not be blank");
    }

    // ========== NULL VIOLATIONS ==========

    @Test
    void testOnboardCustomer_nullFirstName_returnsBadRequest() {
        testValidationError(new CustomerRequest(null, "Doe", "123456789",
                LocalDate.of(1990, 1, 1), "john.doe@email.com", "+1234567890", "123 Main St"),
            "firstName must not be blank");
    }

    @Test
    void testOnboardCustomer_nullLastName_returnsBadRequest() {
        testValidationError(new CustomerRequest("John", null, "123456789",
                LocalDate.of(1990, 1, 1), "john.doe@email.com", "+1234567890", "123 Main St"),
            "lastName must not be blank");
    }

    @Test
    void testOnboardCustomer_nullNationalId_returnsBadRequest() {
        testValidationError(new CustomerRequest("John", "Doe", null,
                LocalDate.of(1990, 1, 1), "john.doe@email.com", "+1234567890", "123 Main St"),
            "nationalId must not be blank");
    }

    @Test
    void testOnboardCustomer_nullBirthDate_returnsBadRequest() {
        testValidationError(new CustomerRequest("John", "Doe", "123456789",
                null, "john.doe@email.com", "+1234567890", "123 Main St"),
            "birthDate must not be null");
    }

    @Test
    void testOnboardCustomer_nullPhone_returnsBadRequest() {
        testValidationError(new CustomerRequest("John", "Doe", "123456789",
                LocalDate.of(1990, 1, 1), "john.doe@email.com", null, "123 Main St"),
            "phone must not be blank");
    }

    @Test
    void testOnboardCustomer_nullAddress_returnsBadRequest() {
        testValidationError(new CustomerRequest("John", "Doe", "123456789",
                LocalDate.of(1990, 1, 1), "john.doe@email.com", "+1234567890", null),
            "address must not be blank");
    }

    // ========== EMAIL VALIDATION VIOLATIONS ==========

    @Test
    void testOnboardCustomer_invalidEmail_returnsBadRequest() {
        testValidationError(new CustomerRequest("John", "Doe", "123456789",
                LocalDate.of(1990, 1, 1), "invalid-email", "+1234567890", "123 Main St"),
            "email must be a well-formed email address");
    }

    @Test
    void testOnboardCustomer_invalidEmailMissingAt_returnsBadRequest() {
        testValidationError(new CustomerRequest("John", "Doe", "123456789",
                LocalDate.of(1990, 1, 1), "john.doeemail.com", "+1234567890", "123 Main St"),
            "email must be a well-formed email address");
    }

    @Test
    void testOnboardCustomer_invalidEmailMissingDomain_returnsBadRequest() {
        testValidationError(new CustomerRequest("John", "Doe", "123456789",
                LocalDate.of(1990, 1, 1), "john@", "+1234567890", "123 Main St"),
            "email must be a well-formed email address");
    }

    @Test
    void testOnboardCustomer_emptyEmail_returnsBadRequest() {
        testValidationError(new CustomerRequest("John", "Doe", "123456789",
                LocalDate.of(1990, 1, 1), "", "+1234567890", "123 Main St"),
            "email must not be blank");
    }

    @Test
    void testOnboardCustomer_nullEmail_returnsBadRequest() {
        testValidationError(new CustomerRequest("John", "Doe", "123456789",
                LocalDate.of(1990, 1, 1), null, "+1234567890", "123 Main St"),
            "email must not be blank");
    }

    // ========== MULTIPLE VIOLATIONS ==========

    @Test
    void testOnboardCustomer_multipleViolations_emptyFirstNameAndLastName_returnsBadRequest() {
        var request = new CustomerRequest("", "", "123456789",
            LocalDate.of(1990, 1, 1), "john.doe@email.com", "+1234567890", "123 Main St");

        testMultipleValidationErrors(request,
            "firstName must not be blank", "lastName must not be blank");
    }

    @Test
    void testOnboardCustomer_multipleViolations_nullBirthDateAndEmptyEmail_returnsBadRequest() {
        var request = new CustomerRequest("John", "Doe", "123456789",
            null, "", "+1234567890", "123 Main St");

        testMultipleValidationErrors(request,
            "birthDate must not be null", "email must not be blank");
    }

    // ========== HELPER METHODS ==========

    private void testValidationError(CustomerRequest request, String expectedMessage) {
        log.info("Testing validation error: {}", expectedMessage);
        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .when()
            .log().all()
            .post("/api/v1/customers")
            .then()
            .log().all()
            .statusCode(400)
            .body("status", equalTo("ERROR"))
            .body("code", equalTo("VE001"))
            .body("message", containsString("Validation Error"))
            .body("body", nullValue())
            .body("errors", notNullValue())
            .body("errors.size()", greaterThan(0))
            .body("errors[0].component", equalTo("customer-service"))
            .body("errors[0].message", equalTo(expectedMessage));
    }

    private void testMultipleValidationErrors(CustomerRequest request, String... expectedMessages) {
        log.info("Testing multiple validation errors");
        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .when()
            .log().all()
            .post("/api/v1/customers")
            .then()
            .log().all()
            .statusCode(400)
            .body("status", equalTo("ERROR"))
            .body("code", equalTo("VE001"))
            .body("message", containsString("Validation Error"))
            .body("body", nullValue())
            .body("errors.size()", equalTo(expectedMessages.length))
            .body("errors[0].component", equalTo("customer-service"))
            .body("errors.message", hasItems(expectedMessages));
    }
}