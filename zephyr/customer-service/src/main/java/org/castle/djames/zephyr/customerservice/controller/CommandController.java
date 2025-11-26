package org.castle.djames.zephyr.customerservice.controller;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.castle.djames.zephyr.customerservice.dto.CustomerDetailResponse;
import org.castle.djames.zephyr.customerservice.dto.CustomerRequest;
import org.castle.djames.zephyr.customerservice.service.CommandService;
import org.castle.djames.zephyr.customerservice.validator.RequestValidator;
import org.castle.djames.zephyr.customerservice.validator.groups.AddCustomerGroup;
import org.castle.djames.zephyr.web.model.Response;
import org.castle.djames.zephyr.web.model.ResponseFactory;
import org.jboss.resteasy.reactive.RestResponse;

@Slf4j
@AllArgsConstructor
@Path("/api/v1/customers")
public class CommandController {

    private CommandService commandService;
    private RequestValidator requestValidator;

    /**
     * Handles customer onboarding by accepting customer details in the request
     * and returning the newly created or updated customer details.
     *
     * @param request the customer data encapsulated in a {@link CustomerRequest}
     *                object, containing details such as first name, last name,
     *                date of birth, email, phone, and address.
     * @return a {@link CustomerDetailResponse} containing detailed information
     * about the onboarded customer, such as ID, name, contact details,
     * KYC status, and timestamps.
     */
    @POST
    public RestResponse<Response<CustomerDetailResponse>> onboardCustomer(CustomerRequest request) {
        log.info("Received request to onboard customer: {}", request);

        requestValidator.validate(request, AddCustomerGroup.class);
        var onboardCustomerResponse = commandService.onboardCustomer(request);
        var response = ResponseFactory.success(onboardCustomerResponse);

        log.info("Customer onboarded successfully: {}", response);
        return RestResponse.ok(response);
    }


    /**
     * Updates an existing customer's information based on the provided request data.
     *
     * @param request the customer data encapsulated in a {@link CustomerRequest}
     *                object, containing details including first name, last name,
     *                date of birth, email, phone, and address.
     * @return a {@link CustomerDetailResponse} containing updated customer details
     * such as ID, name, contact details, KYC status, and timestamps.
     */
    @PUT
    public RestResponse<Response<CustomerDetailResponse>> updateCustomer(CustomerRequest request) {
        log.info("Received request to update customer: {}", request);

        requestValidator.validate(request);


        return null;
    }

}
