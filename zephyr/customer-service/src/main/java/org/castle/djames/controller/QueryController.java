package org.castle.djames.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.castle.djames.dto.CustomerDetailResponse;
import org.castle.djames.dto.CustomerValidationResponse;
import org.castle.djames.service.QueryService;

@RequiredArgsConstructor
@Path("/api/v1/customers")
@Produces(MediaType.APPLICATION_JSON)
public class QueryController {

    private final QueryService queryService;

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
    public CustomerValidationResponse validateCustomer(@PathParam("id") String id) {
        return null;
    }

}
