package org.castle.djames.zephyr.customerservice.service.biz.externalkyc;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/kyc")
@RegisterRestClient(configKey = "kyc-service")
public interface KycService {

    /**
     * Registers a new user for KYC (Know Your Customer) verification through an external service.
     * The registration request contains user details, and the response provides the status and reference details
     * of the KYC process.
     */
    @POST
    @Path("/register")
    KycRegisterResponse register(KycRegisterRequest request);


}
