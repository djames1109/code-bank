package org.castle.djames.zephyr.web.model;

import lombok.Builder;

@Builder
public record ErrorDetail(String code, String message, String component) {
}
