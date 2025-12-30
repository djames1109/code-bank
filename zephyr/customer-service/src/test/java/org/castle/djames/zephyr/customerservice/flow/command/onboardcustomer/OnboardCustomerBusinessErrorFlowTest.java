//package org.castle.djames.zephyr.customerservice.flow.command.onboardcustomer;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.empty;
//import static org.hamcrest.Matchers.equalTo;
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.not;
//import static org.hamcrest.Matchers.notNullValue;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import io.quarkus.test.junit.QuarkusTest;
//import jakarta.ws.rs.core.MediaType;
//import java.util.Optional;
//import org.castle.djames.zephyr.customerservice.entity.Customer;
//import org.castle.djames.zephyr.customerservice.flow.command.BaseCommandTest;
//import org.junit.jupiter.api.Test;
//
//@QuarkusTest
//class OnboardCustomerBusinessErrorFlowTest extends BaseCommandTest {
//
//    @Test
//    void testOnboardCustomer_existingNationalId_returnsConflict() {
//        var nationalId = "123456789";
//
//        var existingCustomer = new Customer();
//        existingCustomer.setNationalId(nationalId);
//
//        when(customerRepository.findByNationalId(anyString()))
//            .thenReturn(Optional.of(existingCustomer));
//
//        var request = """
//            {
//              "firstName": "John",
//              "lastName": "Doe",
//              "nationalId": "%s",
//              "birthDate": "1990-01-01",
//              "email": "john.doe@email.com",
//              "phone": "+1234567890",
//              "address": "123 Main St"
//            }
//            """.formatted(nationalId);
//
//        given()
//            .contentType(MediaType.APPLICATION_JSON)
//            .body(request)
//            .when()
//            .post("/api/v1/customers")
//            .then()
//            .log().all()
//            .statusCode(409)
//            .body("status", equalTo("ERROR"))          // adjust to your enum/string if needed
//            .body("code", notNullValue())              // or equalTo("E409") if you have a constant
//            .body("message", notNullValue())
//            .body("errors", not(empty()))
//            // assuming your first error references nationalId – adjust paths if your error model is different
//            .body("errors[0].field", equalTo("nationalId"))
//            .body("errors[0].message", notNullValue())
//            .body("body", is(notNullValue()));         // or null, depending on how you structure errors
//
//        verify(customerRepository).findByNationalId(nationalId);
//    }
//}
