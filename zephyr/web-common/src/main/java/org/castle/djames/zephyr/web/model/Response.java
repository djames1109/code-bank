package org.castle.djames.zephyr.web.model;

import java.util.List;
import lombok.Builder;

@Builder
public record Response<T>(ResponseStatus status,
                          String code,
                          String message,
                          T body,
                          List<ErrorDetail> errors) {
}
