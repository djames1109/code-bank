package org.castle.djames.zephyr.customerservice.flow.command.onboardcustomer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.castle.djames.zephyr.customerservice.entity.Customer;
import org.castle.djames.zephyr.customerservice.flow.command.BaseCommandTest;
import org.castle.djames.zephyr.customerservice.service.biz.externalkyc.KycRegisterRequest;
import org.junit.jupiter.api.Test;

@QuarkusTest
class OnboardCustomerSuccessFlowTest extends BaseCommandTest {

    @Test
    void testOnboardCustomer_validRequest_persistsCustomerAndReturnsOk() {
        var nationalId = "123456789";

        when(kycService.register(any()))
            .thenReturn(OnboardCustomerExpectations.buildKycRegisterResponse());

        var request = """
            {
              "firstName": "John",
              "lastName": "Doe",
              "nationalId": "%s",
              "birthDate": "1990-01-01",
              "email": "john.doe@email.com",
              "phone": "+1234567890",
              "address": "123 Main St"
            }
            """.formatted(nationalId);

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .when()
            .post("/api/v1/customers")
            .then()
            .log().all()
            .statusCode(200)
            .body("status", equalTo("SUCCESS"))
            .body("code", equalTo("S000"))
            .body("message", equalTo("Success."))
            .body("errors", is(empty()))
            .body("body", is(notNullValue()))
            .body("body.id", is(notNullValue()))
            .body("body.firstName", equalTo("John"))
            .body("body.lastName", equalTo("Doe"))
            .body("body.email", equalTo("john.doe@email.com"))
            .body("body.phone", equalTo("+1234567890"))
            .body("body.kycStatus", equalTo("VERIFIED"))
            .body("body.kycIssuedDate", is(notNullValue()))
            .body("body.kycExpiryDate", is(notNullValue()))
            .body("body.createdDate", is(notNullValue()))
            .body("body.updatedDate", is(notNullValue()));

        // Verify methods are called
        verify(customerRepository).findByNationalId(eq(nationalId));
        verify(customerRepository).persist(any(Customer.class));
        verify(kycService).register(any(KycRegisterRequest.class));
    }
}
