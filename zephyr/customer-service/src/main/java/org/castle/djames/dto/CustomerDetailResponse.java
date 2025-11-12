package org.castle.djames.dto;

import java.time.Instant;

public record CustomerDetailResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String kycStatus, // make enum
        Instant kycIssuedDate,
        Instant kycExpiryDate,
        Instant createdAt,
        Instant updatedAt) {
}
