//package org.castle.djames.zephyr.customerservice.controller;
//
//import io.quarkus.test.Mock;
//import jakarta.ws.rs.core.Response;
//import org.castle.djames.zephyr.customerservice.dto.CustomerDetailResponse;
//import org.castle.djames.zephyr.customerservice.dto.CustomerRequest;
//import org.castle.djames.zephyr.customerservice.exception.ValidationException;
//import org.castle.djames.zephyr.customerservice.service.CommandService;
//import org.castle.djames.zephyr.customerservice.validator.RequestValidator;
//import org.castle.djames.zephyr.customerservice.validator.groups.AddCustomerGroup;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//
//import static com.google.inject.matcher.Matchers.any;
//import static io.restassured.RestAssured.when;
//import static javax.management.Query.eq;
//import static org.junit.jupiter.api.Assertions.*;
//
//class CommandControllerTest {
//
//    @Mock
//    private CommandService commandService;
//
//    @Mock
//    private RequestValidator requestValidator;
//
//    private CommandController commandController;
//
//    @BeforeEach
//    void setUp() {
//        commandController = new CommandController(commandService, requestValidator);
//    }
//
//    @Test
//    void testOnboardCustomer_validRequest_returnsOkResponseWithCustomerDetails() {
//    CustomerRequest request = new CustomerRequest("John", "Doe", "123456789",
//            LocalDate.now(), "john@example.com", "+1234567890", "123 Main St");
//        CustomerDetailResponse expectedResponse = CustomerDetailResponse.builder().build();
//
//        when(commandService.onboardCustomer(request)).thenReturn(expectedResponse);
//        doNothing().when(requestValidator).validate(any(), any());
//
//        Response response = commandController.onboardCustomer(request);
//
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//        assertEquals(expectedResponse, response.getEntity());
//    }
//
//    @Test
//    void testOnboardCustomer_validRequest_invokesValidatorWithAddCustomerGroup() {
//        CustomerRequest request = new CustomerRequest("John", "Doe", "123456789",
//                LocalDate.now(), "john@example.com", "+1234567890", "123 Main St");
//
//        commandController.onboardCustomer(request);
//
//        verify(requestValidator).validate(request, AddCustomerGroup.class);
//    }
//
//    @Test
//    void testOnboardCustomer_validRequest_invokesCommandService() {
//        CustomerRequest request = new CustomerRequest("John", "Doe", "123456789",
//                LocalDate.now(), "john@example.com", "+1234567890", "123 Main St");
//
//        commandController.onboardCustomer(request);
//
//        verify(commandService).onboardCustomer(request);
//    }
//
//    @Test
//    void testOnboardCustomer_invalidRequest_throwsValidationException() {
//        CustomerRequest request = new CustomerRequest("John", "Doe", "123456789",
//                LocalDate.now(), "john@example.com", "+1234567890", "123 Main St");
//
//        doThrow(ValidationException.class)
//                .when(requestValidator).validate(eq(request), eq(AddCustomerGroup.class));
//
//        assertThrows(ValidationException.class, () -> commandController.onboardCustomer(request));
//    }
//
//    @Test
//    void testOnboardCustomer_duplicateNationalId_throwsRuntimeException() {
//        CustomerRequest request = new CustomerRequest("John", "Doe", "123456789",
//                LocalDate.now(), "john@example.com", "+1234567890", "123 Main St");
//
//        when(commandService.onboardCustomer(request)).thenThrow(RuntimeException.class);
//
//        assertThrows(RuntimeException.class, () -> commandController.onboardCustomer(request));
//    }
//
//    /**
//     testOnboardCustomer_validRequest_returnsOkResponseWithCustomerDetails
//        Description: Verify that onboarding a customer with valid data returns HTTP 200 with customer details
//        Requirement: Controller must return RestResponse.ok() with CustomerDetailResponse wrapped in Response object when request is valid
//
//    testOnboardCustomer_validRequest_invokesValidatorWithAddCustomerGroup
//        Description: Verify that the RequestValidator is called with the correct validation group
//        Requirement: Controller must invoke requestValidator.validate() with AddCustomerGroup.class before processing
//
//    testOnboardCustomer_validRequest_invokesCommandService
//        Description: Verify that the CommandService.onboardCustomer() is called with the request
//        Requirement: Controller must delegate business logic to CommandService
//
//    testOnboardCustomer_invalidRequest_throwsValidationException
//        Description: Verify that validation failure throws ValidationException
//        Requirement: Controller must allow validator to throw ValidationException when validation fails
//
//    testOnboardCustomer_duplicateNationalId_throwsRuntimeException
//        Description: Verify that attempting to onboard customer with existing national ID throws RuntimeException
//        Requirement: Controller must propagate RuntimeException from service layer when national ID already exists
//     **/
//
//}