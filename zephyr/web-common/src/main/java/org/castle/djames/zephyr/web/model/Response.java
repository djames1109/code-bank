package org.castle.djames.zephyr.web.model;

import lombok.Builder;

import java.util.List;

@Builder
public record Response<T>(ResponseStatus status,
                          String code,
                          String message,
                          T body,
                          List<ErrorDetail> errors) {
}
