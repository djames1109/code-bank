package org.castle.djames.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import org.castle.djames.dto.CustomerDetailResponse;
import org.castle.djames.dto.CustomerRequest;
import org.castle.djames.service.CommandService;

@AllArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/api/v1/customers")
public class CommandController {

    private CommandService commandService;

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
    public CustomerDetailResponse updateCustomer(CustomerRequest request) {
        return null;
    }

}
