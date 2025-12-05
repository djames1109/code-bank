package org.castle.djames.zephyr.customerservice.service.biz.interceptor;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpClientResponse;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.client.api.ClientLogger;

@Slf4j
@Singleton
public class ExternalServiceLogInterceptor implements ClientLogger {

    @Override
    public void setBodySize(int bodySize) {
        // limit
    }

    @Override
    public void logResponse(HttpClientResponse response, boolean redirect) {
        log.info("Response: status: {}, body: {}", response.statusCode(),
            response.body());
    }

    @Override
    public void logRequest(HttpClientRequest request, Buffer body,
                           boolean omitBody) {
        log.info("Request: headers: {}, body: {}", request.headers(), body);
    }
}
