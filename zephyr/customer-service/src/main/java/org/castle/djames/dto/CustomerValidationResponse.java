package org.castle.djames.dto;

public record CustomerValidationResponse(
        Long id,
        boolean exists,
        String kycStatus) {
}
