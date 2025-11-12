package org.castle.djames.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.castle.djames.dto.CustomerDetailResponse;
import org.castle.djames.dto.CustomerRequest;
import org.castle.djames.dto.CustomerValidationResponse;
import org.castle.djames.service.CustomerService;

@Path("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerDetailResponse onboardCustomer(CustomerRequest request) {

        return null;
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerDetailResponse updateCustomer(CustomerRequest request) {
        return null;
    }


    /**
     * Retrieves the details of a specific customer based on their unique identifier.
     *
     * @param id the unique identifier of the customer whose details are to be fetched
     * @return a {@link CustomerDetailResponse} containing detailed information
     * about the customer, such as ID, name, email, phone, KYC status, and
     * timestamps
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerDetailResponse getCustomerDetails(@PathParam("id") String id) {
        return null;
    }

    /**
     * Validates the existence and KYC status of a customer based on their unique identifier.
     *
     * @param id the unique identifier of the customer to validate
     * @return a {@link CustomerValidationResponse} containing the customer ID,
     * existence flag, and KYC status
     */
    @GET
    @Path("/validate/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerValidationResponse validateCustomer(@PathParam("id") String id) {
        return null;
    }

}
