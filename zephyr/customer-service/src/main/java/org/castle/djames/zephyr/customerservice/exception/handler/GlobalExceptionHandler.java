package org.castle.djames.zephyr.customerservice.exception.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.castle.djames.zephyr.customerservice.exception.BaseZephyrException;
import org.castle.djames.zephyr.customerservice.exception.DuplicateCustomerException;
import org.castle.djames.zephyr.customerservice.exception.ValidationException;
import org.castle.djames.zephyr.web.model.ErrorDetail;
import org.castle.djames.zephyr.web.model.ResponseCode;
import org.castle.djames.zephyr.web.model.ResponseFactory;

@Slf4j
@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    private static final String SVC_COMPONENT = "customer-service";

    @Override
    public Response toResponse(Exception exception) {
        log.error("Exception occurred: ", exception);

        return switch (exception) {
            case ValidationException e -> handleValidationException(e);
            case DuplicateCustomerException e -> handleDuplicateCustomerException(e);
            case BaseZephyrException e -> handleBaseZephyrException(e);
            default -> handleGenericException(exception);
        };
    }

    private Response handleValidationException(ValidationException exception) {
        log.error("Validation error: {}", exception.getMessage());

        var errorDetails = exception.getViolations().stream().map(e -> ErrorDetail.builder()
                .message(e)
                .component(SVC_COMPONENT)
                .build())
            .toList();

        var response = ResponseFactory.failure(ResponseCode.VALIDATION_ERROR, errorDetails);

        return Response.status(Response.Status.BAD_REQUEST)
            .entity(response)
            .build();
    }

    private Response handleDuplicateCustomerException(DuplicateCustomerException exception) {
        log.error("Duplicate customer error: {}", exception.getMessage());

        var errorDetail = ErrorDetail.builder()
            .code("DUPLICATE_CUSTOMER")
            .message(exception.getMessage())
            .component("nationalId")
            .build();

        var response = ResponseFactory.failure(List.of(errorDetail));

        return Response.status(Response.Status.CONFLICT)
            .entity(response)
            .build();
    }

    private Response handleBaseZephyrException(BaseZephyrException exception) {
        log.error("Business error: {}", exception.getMessage());

        var errorDetail = ErrorDetail.builder()
            .code("BUSINESS_ERROR")
            .message(exception.getMessage())
            .component(SVC_COMPONENT)
            .build();

        var response = ResponseFactory.failure(List.of(errorDetail));

        return Response.status(Response.Status.BAD_REQUEST)
            .entity(response)
            .build();
    }

    private Response handleGenericException(Exception exception) {
        log.error("Unexpected error: ", exception);

        var errorDetail = ErrorDetail.builder()
            .code("INTERNAL_SERVER_ERROR")
            .message("An unexpected error occurred")
            .component(SVC_COMPONENT)
            .build();

        var response = ResponseFactory.failure(List.of(errorDetail));

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(response)
            .build();
    }
}
