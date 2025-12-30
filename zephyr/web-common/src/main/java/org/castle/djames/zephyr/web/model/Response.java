package org.castle.djames.zephyr.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record Response<T>(ResponseStatus status,
                          String code,
                          String message,
                          T body,
                          List<ErrorDetail> errors) {
}
