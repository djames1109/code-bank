package org.castle.djames.zephyr.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record ErrorDetail(String code, String message, String component) {
}
