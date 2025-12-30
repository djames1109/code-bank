package org.castle.djames.zephyr.customerservice.flow.command.onboardcustomer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.castle.djames.zephyr.customerservice.dto.CustomerRequest;
import org.castle.djames.zephyr.customerservice.flow.command.BaseCommandTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@Slf4j
@QuarkusTest
class OnboardCustomerValidationErrorFlowTest extends BaseCommandTest {

    @ParameterizedTest(name = "{0} should return Bad Request")
    @CsvSource({
        "emptyFirstName, '', Doe, 123456789, 1990-01-01, john.doe@email.com, +1234567890, 123 Main St",
        "emptyLastName, John, '', 123456789, 1990-01-01, john.doe@email.com, +1234567890, 123 Main St",
        "emptyNationalId, John, Doe, '', 1990-01-01, john.doe@email.com, +1234567890, 123 Main St",
        "nullBirthDate, John, Doe, 123456789, null, john.doe@email.com, +1234567890, 123 Main St",
        "invalidEmail, John, Doe, 123456789, 1990-01-01, invalid-email, +1234567890, 123 Main St",
        "emptyPhone, John, Doe, 123456789, 1990-01-01, john.doe@email.com, '', 123 Main St",
        "emptyAddress, John, Doe, 123456789, 1990-01-01, john.doe@email.com, +1234567890, ''"
    })
    void testOnboardCustomer_invalidInput_returnsBadRequest(String testCase,
                                                            String firstName,
                                                            String lastName,
                                                            String nationalId,
                                                            String birthDate,
                                                            String email,
                                                            String phone,
                                                            String address) {

        var request = new CustomerRequest(
            unwrapQuotes(firstName),
            unwrapQuotes(lastName),
            unwrapQuotes(nationalId),
            parseDate(birthDate),
            unwrapQuotes(email),
            unwrapQuotes(phone),
            unwrapQuotes(address)
        );

        log.info("Testing {}, expected Bad Request", testCase);
        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .when()
            .post("/api/v1/customers")
            .then()
            .log().all()
            .statusCode(400)
            .body("status", equalTo("ERROR"))
            .body("code", notNullValue())
            .body("message", notNullValue());
    }

    private String unwrapQuotes(String value) {
        return "''".equals(value) ? "" : value;
    }

    private LocalDate parseDate(String date) {
        return "null".equals(date) ? null : LocalDate.parse(date);
    }
}
