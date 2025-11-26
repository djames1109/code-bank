package org.castle.djames.zephyr.customerservice.dto;

public record CustomerValidationResponse(
        Long id,
        boolean exists,
        String kycStatus) {
}
