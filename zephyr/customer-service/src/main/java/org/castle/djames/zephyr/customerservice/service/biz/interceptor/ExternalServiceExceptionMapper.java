package org.castle.djames.zephyr.customerservice.service.biz.interceptor;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

@Slf4j
@Provider
public class ExternalServiceExceptionMapper implements ResponseExceptionMapper<RuntimeException> {

    @Override
    public RuntimeException toThrowable(Response response) {
        if (response.getStatus() >= 400) {
            log.error("External service returned error response: {}", response);
            return new RuntimeException(
                "External service returned error response"); // return custom exception
        }

        return null;
    }
}
