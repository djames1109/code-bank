package org.castle.djames.zephyr.customerservice.controller;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.castle.djames.zephyr.customerservice.dto.CustomerDetailResponse;
import org.castle.djames.zephyr.customerservice.dto.CustomerRequest;
import org.castle.djames.zephyr.customerservice.exception.ValidationException;
import org.castle.djames.zephyr.customerservice.service.CommandService;
import org.castle.djames.zephyr.customerservice.validator.RequestValidator;
import org.castle.djames.zephyr.customerservice.validator.groups.AddCustomerGroup;
import org.castle.djames.zephyr.customerservice.validator.groups.UpdateCustomerGroup;
import org.castle.djames.zephyr.web.model.Response;
import org.castle.djames.zephyr.web.model.ResponseFactory;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;

@Slf4j
@AllArgsConstructor
@Path("/api/v1/customers")
public class CommandController {

    private CommandService commandService;
    private RequestValidator requestValidator;

    /**
     * Onboards a new customer based on the provided request data.
     * This process includes validating the request, performing customer registration,
     * and generating a response that encapsulates the customer's details.
     *
     * @param request the customer data encapsulated in a {@link CustomerRequest} object,
     *                including first name, last name, national ID, birthdate, email, phone, and address.
     * @return a {@link RestResponse} object wrapping a {@link Response} with the onboarded customer's details,
     * encapsulated in a {@link CustomerDetailResponse}.
     * @throws ValidationException if the provided request data fails validation.
     * @throws RuntimeException    if a customer with the same national ID is already registered.
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
    @Path("/{id}")
    public RestResponse<Response<CustomerDetailResponse>> updateCustomer(@RestPath Long id, CustomerRequest request) {
        log.info("Received request to update customer: {}", request);

        requestValidator.validate(request, UpdateCustomerGroup.class);
        var updateCustomerResponse = commandService.updateCustomer(id, request);
        var response = ResponseFactory.success(updateCustomerResponse);

        log.info("Customer updated successfully: {}", response);
        return RestResponse.ok(response);
    }

}
