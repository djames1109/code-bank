package org.castle.djames.service.biz.externalkyc;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/kyc")
@RegisterRestClient(configKey = "kyc-service")
public interface KycService {

    @POST
    @Path("/register")
    KycRegisterResponse register(KycRegisterRequest request);

}
